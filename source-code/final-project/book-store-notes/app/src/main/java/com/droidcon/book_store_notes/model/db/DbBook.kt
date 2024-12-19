package com.droidcon.book_store_notes.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidcon.book_store_notes.authorsToString
import com.droidcon.book_store_notes.model.Volume
import com.droidcon.book_store_notes.model.db.Constants.BOOK_TABLE

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