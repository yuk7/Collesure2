package com.example.collesure2.data.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern

class EngineGoogle:SearchEngine {
    override fun SearchImage(word: String, nsfw: Boolean): ArrayList<String> {
        val imageUrlList = ParseHtml(getHtml(word))
        return imageUrlList
    }

    fun ParseHtml(html_txt:String):ArrayList<String>
    {
        val regex = "<img.+?src=\"(.+?)\".+?>"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(html_txt)
        val imageUrlList = arrayListOf<String>()
        while (matcher.find()) {
            imageUrlList.add(matcher.group(1))
        }
        return imageUrlList
    }

    fun getHtml(word: String): String {
        val client = OkHttpClient()
        val req = Request.Builder().url("http://www.google.com/search?q=${word}&tbm=isch")
            .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98)").get().build()
        val resp = client.newCall(req).execute()
        return resp.body!!.string()
    }
}