package com.droidcon.bookstorenotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.bookstorenotes.model.Note
import com.droidcon.bookstorenotes.model.Volume
import com.droidcon.bookstorenotes.model.db.CollectionDbRepo
import com.droidcon.bookstorenotes.model.db.DbBook
import com.droidcon.bookstorenotes.model.db.DbNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(private val repo: CollectionDbRepo) : ViewModel() {

    val currentBook = MutableStateFlow<DbBook?>(null)
    val collection = MutableStateFlow<List<DbBook>>(listOf())
    val notes = MutableStateFlow<List<DbNote>>(listOf())

    init {
        getCollection()
        getNotes()
    }

    fun setCurrentBookId(bookId: String?) {
        bookId?.let {
            viewModelScope.launch {
                repo.getBookFromRepo(it).collect {
                    currentBook.value = it
                }
            }
        }
    }

    fun addBook(book: Volume) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addBookToRepo(DbBook.fromVolume(book))
        }
    }

    fun getCollection() {
        viewModelScope.launch {
            repo.getBooksFromRepo().collect {
                collection.value = it
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            repo.getAllNotes().collect {
                notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addNoteToRepo(DbNote.fromNote(note))
        }
    }

    fun deleteNote(note: DbNote) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNoteFromRepo(note)
        }
    }

    fun deleteBook(book: DbBook) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllNotes(book)
            repo.deleteBookFromRepo(book)
        }
    }
}









