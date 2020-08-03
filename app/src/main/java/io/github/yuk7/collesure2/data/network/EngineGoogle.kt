package io.github.yuk7.collesure2.data.network

import android.util.Log
import io.github.yuk7.collesure2.data.ImageItem
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.regex.Pattern
import okhttp3.HttpUrl
import org.json.JSONArray


class EngineGoogle : SearchEngine {
    override fun searchImage(
        word: String,
        start: Int,
        count: Int,
        nsfw: Boolean
    ): ArrayList<ImageItem> {
        val allItems = searchAllImage(word, nsfw)
        var items = arrayListOf<ImageItem>()

        if (start <= allItems.size) {
            var indexTo = start + count
            if (indexTo >= allItems.size) {
                indexTo = allItems.size - start
            }
            items.addAll(allItems.subList(0, indexTo))
        }
        return items
    }

    private fun searchAllImage(word: String, nsfw: Boolean): ArrayList<ImageItem> {
        var ds2 = parseHtml(getHtml(word, nsfw))
        val json_items = parseJson(ds2)
        val items = arrayListOf<ImageItem>()
        for(i in 0 until json_items.length()) {
            val json_item = json_items.getJSONArray(i)
            if(json_item.getInt(0) == 1) {
                val json_item_img = json_item.getJSONArray(1)
                val item = ImageItem()
                Log.d("item", json_item_img.toString())
                item.imageUrl = json_item_img.getJSONArray(3).getString(0)
                item.thumbIUrl = json_item_img.getJSONArray(2).getString(0)
                if (json_item_img.getBoolean(8)) {
                    item.url = json_item_img.getJSONObject(11).getJSONArray("2003").getString(2)
                    item.tag = json_item_img.getJSONObject(11).getJSONArray("2003").getString(3)
                } else {
                    item.url = json_item_img.getJSONObject(9).getJSONArray("2003").getString(2)
                    item.tag = json_item_img.getJSONObject(9).getJSONArray("2003").getString(3)
                }
                items.add(item)
            }
        }
        return items
    }

    private fun parseJson(json: String): JSONArray {
        val root = JSONArray(json)
        val r31_0_12_2 = root.getJSONArray(31).getJSONArray(0).getJSONArray(12).getJSONArray(2)
        return r31_0_12_2
    }

    private fun parseHtml(html_txt: String): String {
        val regex = "AF_initDataCallback\\(\\{key: 'ds:1', isError:  false , hash: '2', data:((?m).+?)\\}\\);</script>"
        val pattern = Pattern.compile(regex, Pattern.DOTALL)
        val matcher = pattern.matcher(html_txt)
        matcher.find()
        return matcher.group(1)!!
    }

    private fun getHtml(word: String, nsfw: Boolean): String {
        val client = OkHttpClient()
        var urlbuilder = HttpUrl.Builder()
            .scheme("https")
            .host("www.google.com")
            .addPathSegment("search")
            .addQueryParameter("q", word)
            .addQueryParameter("tbm", "isch")

        when (nsfw) {
            true -> urlbuilder.addQueryParameter("safe", "off")
            false -> urlbuilder.addQueryParameter("safe", "active")
        }

        val req = Request.Builder().url(urlbuilder.build())
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4068.5 Safari/537.36"
            ).get().build()
        val resp = client.newCall(req).execute()
        return resp.body!!.string()
    }
}