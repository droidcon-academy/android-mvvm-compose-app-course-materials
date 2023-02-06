package com.droidcon.bookstorenotes.model.api

import androidx.compose.runtime.mutableStateOf
import com.droidcon.bookstorenotes.model.GoogleBooksApiResponse
import com.droidcon.bookstorenotes.model.Volume
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookApiRepo(private val api: BookApi) {

    val books = MutableStateFlow<NetworkResult<GoogleBooksApiResponse>>(NetworkResult.Initial())
    val bookDetails = mutableStateOf<Volume?>(null)

    fun query(query: String) {
        books.value = NetworkResult.Loading()
        api.getVolumes(query)
            .enqueue(object : Callback<GoogleBooksApiResponse> {
                override fun onResponse(
                    call: Call<GoogleBooksApiResponse>,
                    response: Response<GoogleBooksApiResponse>
                ) {
                    if (response.isSuccessful)
                        response.body()?.let {
                            books.value = NetworkResult.Success(it)
                        }
                    else
                        books.value = NetworkResult.Error(response.message())
                }

                override fun onFailure(call: Call<GoogleBooksApiResponse>, t: Throwable) {
                    t.localizedMessage?.let { books.value = NetworkResult.Error(it) }
                    t.printStackTrace()
                }

            })
    }

    fun getSingleBook(id: String) {
        bookDetails.value = books.value.data?.items?.firstOrNull { book -> book.id == id }
    }
}