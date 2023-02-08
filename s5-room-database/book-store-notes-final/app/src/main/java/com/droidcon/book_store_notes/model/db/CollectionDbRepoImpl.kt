package com.droidcon.book_store_notes.model.db

class CollectionDbRepoImpl(private val bookDao: BookDao): CollectionDbRepo {
    override suspend fun getBooksFromRepo() = bookDao.getBooks()

    override suspend fun getBookFromRepo(bookId: String) = bookDao.getBook(bookId)

    override suspend fun addBookToRepo(book: DbBook) = bookDao.addBook(book)

    override suspend fun updateBookInRepo(book: DbBook) = bookDao.updateBook(book)

    override suspend fun deleteBookFromRepo(book: DbBook) = bookDao.deleteBook(book)

}