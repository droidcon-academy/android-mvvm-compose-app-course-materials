package com.droidcon.book_store_notes.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObservable {
    enum class Status {
        Available,
        Unavailable
    }

    fun observe(): Flow<Status>
}