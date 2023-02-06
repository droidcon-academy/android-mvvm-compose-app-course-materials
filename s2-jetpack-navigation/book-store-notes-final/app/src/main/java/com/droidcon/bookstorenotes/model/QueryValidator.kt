package com.droidcon.bookstorenotes.model

object QueryValidator {
    fun validateQuery(query: String): Boolean = query.length >= 2
}