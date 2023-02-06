package com.droidcon.bookstorenotes.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {
    suspend fun getBooksFromRepo(): Flow<List<DbBook>>

    suspend fun getBookFromRepo(bookId: String): Flow<DbBook>

    suspend fun addBookToRepo(book: DbBook)

    suspend fun updateBookInRepo(book: DbBook)

    suspend fun deleteBookFromRepo(book: DbBook)


    suspend fun getAllNotes(): Flow<List<DbNote>>

    suspend fun getNotesFromRepo(bookId: Int): Flow<List<DbNote>>

    suspend fun addNoteToRepo(note: DbNote)

    suspend fun updateNoteInRepo(note: DbNote)

    suspend fun deleteNoteFromRepo(note: DbNote)

    suspend fun deleteAllNotes(book: DbBook)
}