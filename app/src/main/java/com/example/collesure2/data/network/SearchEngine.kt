package com.example.collesure2.data.network

interface SearchEngine {
    fun SearchImage(word:String,nsfw:Boolean):ArrayList<String>{
        return arrayListOf<String>();
    }
}