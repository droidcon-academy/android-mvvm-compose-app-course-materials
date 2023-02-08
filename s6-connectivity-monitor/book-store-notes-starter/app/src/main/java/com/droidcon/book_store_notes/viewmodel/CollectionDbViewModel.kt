package com.droidcon.book_store_notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.book_store_notes.model.Volume
import com.droidcon.book_store_notes.model.db.CollectionDbRepo
import com.droidcon.book_store_notes.model.db.DbBook
import com.droidcon.book_store_notes.model.db.DbNote
import com.droidcon.book_store_notes.model.db.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDbViewModel @Inject constructor(private val repo: CollectionDbRepo) : ViewModel() {

    val collection = MutableStateFlow<List<DbBook>>(listOf())
    val currentBook = MutableStateFlow<DbBook?>(null)
    val notes = MutableStateFlow<List<DbNote>>(listOf())

    init {
        getCollection()
        getNotes()
    }

    private fun getCollection() {
        viewModelScope.launch {
            repo.getBooksFromRepo().collect {
                collection.value = it
            }
        }
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

    fun deleteBook(book: DbBook) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllNotes(book)
            repo.deleteBookFromRepo(book)
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

}