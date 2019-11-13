package com.example.collesure2.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import okhttp3.HttpUrl


class EngineGoogle : SearchEngine {
    override fun SearchImage(word: String, page: Int, nsfw: Boolean): ArrayList<String> {
        return parseHtml(getHtml(word, page, nsfw))
    }

    private fun parseHtml(html_txt: String): ArrayList<String> {
        val regex = "<img.+?src=\"(.+?)\".+?>"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(html_txt)
        val imageUrlList = arrayListOf<String>()
        while (matcher.find()) {
            imageUrlList.add(matcher.group(1))
        }
        return imageUrlList
    }

    private fun getHtml(word: String, page: Int, nsfw: Boolean): String {
        val client = OkHttpClient()
        var urlbuilder = HttpUrl.Builder()
            .scheme("http")
            .host("www.google.com")
            .addPathSegment("search")
            .addQueryParameter("q", word)
            .addQueryParameter("tbm", "isch")
            .addQueryParameter("start", (page * 20).toString())

        when (nsfw) {
            true -> urlbuilder.addQueryParameter("safe", "off")
            false -> urlbuilder.addEncodedQueryParameter("safe", "active")
        }

        val req = Request.Builder().url(urlbuilder.build())
            .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98)").get().build()
        val resp = client.newCall(req).execute()
        return resp.body!!.string()
    }
}