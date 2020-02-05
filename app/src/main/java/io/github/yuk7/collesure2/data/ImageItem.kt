package io.github.yuk7.collesure2.data

import io.github.yuk7.collesure2.data.repository.favorite.Favorite
import java.io.Serializable

class ImageItem : Favorite(), Serializable {
    companion object {
        fun fromFavorite(from: Favorite): ImageItem {
            var to = ImageItem()
            to.thumbIUrl = from.thumbIUrl
            to.imageUrl = from.imageUrl
            to.url = from.url
            to.tag = from.tag
            return to
        }
    }
}