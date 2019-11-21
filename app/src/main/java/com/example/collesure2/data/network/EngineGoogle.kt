package com.example.collesure2.data.network

import com.example.collesure2.data.ImageItem
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import okhttp3.HttpUrl


class EngineGoogle : SearchEngine {
    override fun searchImage(word: String, start: Int, count: Int, nsfw: Boolean): ArrayList<ImageItem> {
        val allItems = searchAllImage(word, nsfw)
        var items = arrayListOf<ImageItem>()

        if(start <= allItems.size) {
            var indexTo = start + count
            if(indexTo >= allItems.size){
                indexTo = allItems.size - start
            }
            items.addAll(allItems.subList(0, indexTo))
        }
        return items
    }

    private fun searchAllImage(word: String, nsfw: Boolean): ArrayList<ImageItem> {
        var jsons = parseHtml(getHtml(word, nsfw))
        var items = arrayListOf<ImageItem>()
        for (json in jsons) {
            items.add(parseJson(json))
        }
        return items
    }

    private fun parseJson(json_txt: String):ImageItem {
        val regex_orig = "\"ou\":\"(.+?)\""
        val regex_thumb = "\"tu\":\"(.+?)\""
        val regex_url = "\"ru\":\"(.+?)\""
        val matcher_orig = Pattern.compile(regex_orig).matcher(json_txt)
        val matcher_thumb = Pattern.compile(regex_thumb).matcher(json_txt)
        val matcher_url = Pattern.compile(regex_url).matcher(json_txt)

        var item = ImageItem()
        if(matcher_orig.find()){
            item.imageUrl = urlDeEscape(matcher_orig.group(1)!!)
        }
        if(matcher_thumb.find()){
            var thumbUrl = urlDeEscape(matcher_thumb.group(1)!!)
            item.thumbIUrl = thumbUrl
        }
        if(matcher_url.find()){
            item.url = urlDeEscape(matcher_url.group(1)!!)
        }
        return item
    }

    private fun urlDeEscape(urlStr: String):String {
        var result = urlStr
        result = result.replace("\\u003d","=")
        result = result.replace("\\u0026","&")
        result = result.replace("\\u003c","<")
        result = result.replace("\\u003e",">")

        return result
    }

    private fun parseHtml(html_txt: String): ArrayList<String> {
        val regex = "<div class=\"rg_meta notranslate\">(.+?)</div>"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(html_txt)
        val imageJsonList = arrayListOf<String>()
        while (matcher.find()) {
            imageJsonList.add(matcher.group(1)!!)
        }
        return imageJsonList
    }

    private fun getHtml(word: String, nsfw: Boolean): String {
        val client = OkHttpClient()
        var urlbuilder = HttpUrl.Builder()
            .scheme("http")
            .host("www.google.com")
            .addPathSegment("search")
            .addQueryParameter("q", word)
            .addQueryParameter("tbm", "isch")

        when (nsfw) {
            true -> urlbuilder.addQueryParameter("safe", "off")
            false -> urlbuilder.addQueryParameter("safe", "active")
        }

        val req = Request.Builder().url(urlbuilder.build())
            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36").get().build()
        val resp = client.newCall(req).execute()
        return resp.body!!.string()
    }
}