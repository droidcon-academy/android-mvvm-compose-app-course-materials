package com.droidcon.bookstorenotes.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidcon.bookstorenotes.Constants.BOOK_TABLE
import com.droidcon.bookstorenotes.authorsToString
import com.droidcon.bookstorenotes.model.Volume

@Entity(tableName = BOOK_TABLE)
data class DbBook(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: String?,
    val title: String?,
    val authors: String?,
    val thumbnail: String?,
    val description: String?,
) {
    companion object {
        fun fromVolume(volume: Volume) =
            DbBook(
                id = 0,
                apiId = volume.id,
                title = volume.volumeInfo.title ?: "",
                authors = volume.volumeInfo.authors?.authorsToString(),
                thumbnail = volume.volumeInfo.imageLinks?.thumbnail,
                description = volume.volumeInfo.description
            )
    }
}