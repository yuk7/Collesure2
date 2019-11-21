package com.example.collesure2.data.repository.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class History {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "word")
    var word: String = ""

    @ColumnInfo(name = "engine")
    var engine: String = ""
}