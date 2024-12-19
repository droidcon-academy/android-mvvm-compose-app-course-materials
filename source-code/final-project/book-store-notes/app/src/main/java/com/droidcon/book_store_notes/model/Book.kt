package com.droidcon.book_store_notes.model

data class GoogleBooksApiResponse(
    val kind: String?,
    val totalItems: Int?,
    val items: List<Volume>
)

data class Volume(
    val id: String?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: List<String>?,
    val imageLinks: VolumeImageLinks?,
    val language: String?
)

data class VolumeImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)