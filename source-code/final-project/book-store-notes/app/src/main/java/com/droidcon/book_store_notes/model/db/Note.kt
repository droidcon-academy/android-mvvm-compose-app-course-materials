package com.droidcon.book_store_notes.model.db

data class Note(
    val bookId: Int,
    val title: String,
    val text: String
)