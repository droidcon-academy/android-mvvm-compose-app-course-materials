package com.droidcon.book_store_notes.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {
    suspend fun getBooksFromRepo(): Flow<List<DbBook>>

    suspend fun getBookFromRepo(bookId: String): Flow<DbBook>

    suspend fun addBookToRepo(book: DbBook)

    suspend fun updateBookInRepo(book: DbBook)

    suspend fun deleteBookFromRepo(book: DbBook)
}