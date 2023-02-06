package com.droidcon.bookstorenotes.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidcon.bookstorenotes.Constants
import com.droidcon.bookstorenotes.model.Note

@Entity(tableName = Constants.NOTE_TABLE)
data class DbNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val bookId: Int,
    val title: String,
    val text: String,
) {
    companion object {
        fun fromNote(note: Note) =
            DbNote(
                id = 0,
                bookId = note.bookId,
                title = note.title,
                text = note.text
            )
    }
}