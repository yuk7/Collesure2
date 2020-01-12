package com.example.collesure2.data.repository.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAll(): List<History>

    @Query("SELECT * FROM history GROUP BY word ORDER BY id DESC")
    fun getGroup(): List<History>

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    fun findById(id: Int): History

    @Insert
    fun insert(history: History)

    @Query("DELETE FROM history WHERE word = :word")
    fun deleteByWord(word: String)

    @Delete
    fun delete(history: History)

    @Query("DELETE FROM history")
    fun deleteAll()
}