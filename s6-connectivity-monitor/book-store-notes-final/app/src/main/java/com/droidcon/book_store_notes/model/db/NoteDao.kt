package com.droidcon.book_store_notes.model.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Constants.NOTE_TABLE} ORDER BY id ASC")
    fun getAllNotes(): Flow<List<DbNote>>

    @Query("SELECT * FROM ${Constants.NOTE_TABLE} WHERE bookId = :bookId ORDER BY id ASC")
    fun getNotes(bookId: Int): Flow<List<DbNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: DbNote)

    @Update
    fun updateNote(note: DbNote)

    @Delete
    fun deleteNote(note: DbNote)

    @Query("DELETE FROM ${Constants.NOTE_TABLE} WHERE bookId = :bookId")
    fun deleteAllNotes(bookId: Int)

}