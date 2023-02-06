package com.droidcon.bookstorenotes.model.api

import com.droidcon.bookstorenotes.model.GoogleBooksApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    fun getVolumes(@Query("q") query: String): Call<GoogleBooksApiResponse>
}