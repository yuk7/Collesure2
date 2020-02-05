package io.github.yuk7.collesure2.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.yuk7.collesure2.data.repository.favorite.Favorite
import io.github.yuk7.collesure2.data.repository.favorite.FavoriteDao
import io.github.yuk7.collesure2.data.repository.history.History
import io.github.yuk7.collesure2.data.repository.history.HistoryDao

@Database(entities = [Favorite::class, History::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: AppDB? = null

        fun getInstance(context: Context): AppDB {
            if (instance == null) {
                synchronized(AppDB::class) {
                    instance =
                        Room.databaseBuilder(context.applicationContext, AppDB::class.java, "data")
                            .build()
                }
            }
            return instance!!
        }

        fun destoroyInstance() {
            instance = null
        }
    }
}