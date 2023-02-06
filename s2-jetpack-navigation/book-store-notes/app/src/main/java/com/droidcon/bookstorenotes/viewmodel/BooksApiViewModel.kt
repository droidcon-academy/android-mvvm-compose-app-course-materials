package com.droidcon.bookstorenotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.bookstorenotes.connectivity.ConnectivityMonitor
import com.droidcon.bookstorenotes.model.QueryValidator
import com.droidcon.bookstorenotes.model.api.BookApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksApiViewModel @Inject constructor(
    private val repo: BookApiRepo,
    connectivityMonitor: ConnectivityMonitor
) : ViewModel() {
    val result = repo.books
    val bookDetails = repo.bookDetails
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)
    val networkAvailable = connectivityMonitor

    init {
        performQuery()
    }

    private fun performQuery() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { QueryValidator.validateQuery(it) }
                .debounce(1000)
                .collect {
                    repo.query(it)
                }
        }
    }

    fun onQueryUpdate(input: String) {
        queryText.value = input
        queryInput.trySend(input)
    }

    fun getSingleBook(id: String) {
        repo.getSingleBook(id)
    }

}