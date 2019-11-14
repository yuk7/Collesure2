package com.example.collesure2.data.repository.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id LIMIT 1")
    fun findById(id: Int): Favorite

    @Query("SELECT * FROM favorite WHERE thumbIUrl = :thumbIUrl")
    fun findByThumbUrl(thumbIUrl: String): List<Favorite>

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun deleteall()
}