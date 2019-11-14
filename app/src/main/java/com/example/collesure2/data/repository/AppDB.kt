package com.example.collesure2.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.collesure2.data.repository.favorite.Favorite
import com.example.collesure2.data.repository.favorite.FavoriteDao
import com.example.collesure2.data.repository.history.History
import com.example.collesure2.data.repository.history.HistoryDao

@Database(entities = [Favorite::class,History::class], version = 1)
abstract class AppDB: RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
}