package com.example.collesure2.data.repository.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    fun findById(id: Int): History

    @Query("UPDATE history SET count = :count WHERE id = :id")
    fun updateCount(id: Int, count: Int)

    @Insert
    fun insert(history: History)

    @Delete
    fun delete(history: History)

    @Query("DELETE FROM history")
    fun deleteall()
}