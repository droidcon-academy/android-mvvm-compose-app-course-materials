package com.droidcon.book_store_notes.model.db

import androidx.room.*
import com.droidcon.book_store_notes.model.db.Constants.BOOK_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM $BOOK_TABLE ORDER BY id ASC")
    fun getBooks(): Flow<List<DbBook>>

    @Query("SELECT * FROM $BOOK_TABLE WHERE apiId = :bookId")
    fun getBook(bookId: String): Flow<DbBook>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBook(book: DbBook)

    @Update
    fun updateBook(book: DbBook)

    @Delete
    fun deleteBook(book: DbBook)
}