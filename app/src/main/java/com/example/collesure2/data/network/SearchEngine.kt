package com.example.collesure2.data.network

interface SearchEngine {
    fun searchImage(word: String, nsfw: Boolean): ArrayList<String> {
        return searchImage(word,0,nsfw)
    }

    fun searchImage(word: String, page:Int, nsfw: Boolean): ArrayList<String> {
        return arrayListOf()
    }
}