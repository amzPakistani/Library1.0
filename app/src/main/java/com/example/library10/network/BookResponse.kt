package com.example.library10.network

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>
)

@Serializable
data class BookItem(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null
) {
    val allAuthorsx: String
        get() = allAuthors()

    fun allAuthors(): String {
        var x = ""
        if (authors != null) {
            for (author in authors) {
                x += "$author, "
            }
        }
        return x.trimEnd(',', ' ')
    }
}



@Serializable
data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean
) {
    val isForSale: Boolean
        get() = saleability == "FOR_SALE"
}

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
) {
    val httpsThumbnail: String
        get() = thumbnail.replace("http", "https")
}

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

@Serializable
data class ReadingModes(
    val text: Boolean,
    val image: Boolean
)

@Serializable
data class PanelizationSummary(
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean
)

