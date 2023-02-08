package com.droidcon.book_store_notes

import android.content.Context
import androidx.room.Room
import com.droidcon.book_store_notes.model.api.BookApiRepo
import com.droidcon.book_store_notes.model.api.BookService
import com.droidcon.book_store_notes.model.db.*
import com.droidcon.book_store_notes.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = BookApiRepo(BookService.api)

    @Provides
    fun provideBookDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDB::class.java, DB).build()

    @Provides
    fun provideBookDao(collectionDB: CollectionDB) = collectionDB.bookDao()

    @Provides
    fun provideNoteDao(collectionDB: CollectionDB) = collectionDB.noteDao()

    @Provides
    fun provideDbRepo(bookDao: BookDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionDbRepoImpl(bookDao, noteDao)

}