package com.example.collesure2.data

import com.example.collesure2.data.repository.favorite.Favorite
import java.io.Serializable

class ImageItem: Favorite(), Serializable {
    companion object {
        fun fromFavorite(from: Favorite): ImageItem {
            var to = ImageItem()
            to.thumbIUrl = from.thumbIUrl
            to.imageUrl = from.imageUrl
            to.url = from.url
            return to
        }
    }
}