package com.droidcon.book_store_notes.model.db

class CollectionDbRepoImpl(private val bookDao: BookDao, private val noteDao: NoteDao) :
    CollectionDbRepo {
    override suspend fun getBooksFromRepo() = bookDao.getBooks()

    override suspend fun getBookFromRepo(bookId: String) = bookDao.getBook(bookId)

    override suspend fun addBookToRepo(book: DbBook) = bookDao.addBook(book)

    override suspend fun updateBookInRepo(book: DbBook) = bookDao.updateBook(book)

    override suspend fun deleteBookFromRepo(book: DbBook) = bookDao.deleteBook(book)


    override suspend fun getAllNotes() = noteDao.getAllNotes()

    override suspend fun getNotesFromRepo(bookId: Int) = noteDao.getNotes(bookId)

    override suspend fun addNoteToRepo(note: DbNote) = noteDao.addNote(note)

    override suspend fun updateNoteInRepo(note: DbNote)  = noteDao.updateNote(note)

    override suspend fun deleteNoteFromRepo(note: DbNote) = noteDao.deleteNote(note)

    override suspend fun deleteAllNotes(book: DbBook) = noteDao.deleteAllNotes(book.id)
}