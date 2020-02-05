package io.github.yuk7.collesure2.data.repository.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite ORDER BY CASE WHEN :isAsc = 1 THEN id END ASC, CASE WHEN :isAsc = 0 THEN id END DESC")
    fun getAll(isAsc: Boolean): List<Favorite>

    @Query("SELECT * FROM favorite WHERE tag LIKE :query ORDER BY CASE WHEN :isAsc = 1 THEN id END ASC, CASE WHEN :isAsc = 0 THEN id END DESC")
    fun searchByTag(query: String, isAsc: Boolean): List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id LIMIT 1")
    fun findById(id: Int): Favorite?

    @Query("SELECT * FROM favorite WHERE thumburl = :thumbIUrl LIMIT 1")
    fun findByThumbUrl(thumbIUrl: String): Favorite?

    @Query("SELECT * FROM favorite WHERE imageurl = :imageUrl LIMIT 1")
    fun findByImageUrl(imageUrl: String): Favorite?

    @Insert
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE imageurl = :imageUrl")
    fun deleteByImageUrl(imageUrl: String)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}