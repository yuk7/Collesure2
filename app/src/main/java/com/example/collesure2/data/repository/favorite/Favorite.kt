package com.example.collesure2.data.repository.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Favorite {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "thumburl")
    var thumbIUrl: String = ""

    @ColumnInfo(name = "imageurl")
    var imageUrl: String = ""

    @ColumnInfo(name = "url")
    var url: String = ""

    @ColumnInfo(name = "tag")
    var tag: String = ""
}