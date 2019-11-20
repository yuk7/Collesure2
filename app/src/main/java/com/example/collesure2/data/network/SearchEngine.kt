package com.example.collesure2.data.network

import com.example.collesure2.data.ImageItem

interface SearchEngine {
    fun searchImage(word: String, nsfw: Boolean): ArrayList<ImageItem> {
        return searchImage(word,0,nsfw)
    }

    fun searchImage(word: String, page:Int, nsfw: Boolean): ArrayList<ImageItem> {
        return arrayListOf()
    }
}