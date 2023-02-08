package com.droidcon.book_store_notes.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BookService {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: BookApi = getRetrofit().create(BookApi::class.java)
}