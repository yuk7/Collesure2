package com.example.collesure2.data.network

interface SearchEngine {
    fun SearchImage(word: String, nsfw: Boolean): ArrayList<String> {
        return SearchImage(word,0,nsfw)
    }

    fun SearchImage(word: String, page:Int, nsfw: Boolean): ArrayList<String> {
        return arrayListOf<String>()
    }
}