package com.droidcon.book_store_notes

fun validateQuery(query: String) = query.length >= 2

fun List<String>.authorsToString() = this.joinToString(separator = ", ")