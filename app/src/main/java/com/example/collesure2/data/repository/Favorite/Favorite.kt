package com.example.collesure2.data.repository.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Favorite {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo(name = "thumbIUrl")
    var thumbIUrl: String = ""

    @ColumnInfo(name = "ImageUrl")
    var imageUrl: String = ""

    @ColumnInfo(name = "Url")
    var Url: String = ""
}