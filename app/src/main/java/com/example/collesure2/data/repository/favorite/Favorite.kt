package com.example.collesure2.data.repository.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Favorite {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "ThumbIUrl")
    var thumbIUrl: String = ""

    @ColumnInfo(name = "ImageUrl")
    var imageUrl: String = ""

    @ColumnInfo(name = "Url")
    var url: String = ""
}