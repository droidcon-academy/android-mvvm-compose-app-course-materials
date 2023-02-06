package com.droidcon.bookstorenotes

import android.content.Context
import androidx.room.Room
import com.droidcon.bookstorenotes.Constants.DB
import com.droidcon.bookstorenotes.connectivity.ConnectivityMonitor
import com.droidcon.bookstorenotes.model.api.BookApiRepo
import com.droidcon.bookstorenotes.model.api.BookService
import com.droidcon.bookstorenotes.model.db.*
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
        Room.databaseBuilder(context, CollectionDB::class.java, DB)
            .build()

    @Provides
    fun provideBookDao(collectionDB: CollectionDB) = collectionDB.bookDao()

    @Provides
    fun provideNoteDao(collectionDB: CollectionDB) = collectionDB.noteDao()

    @Provides
    fun provideDbRepo(bookDao: BookDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionDbRepoImpl(bookDao, noteDao)

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)
}