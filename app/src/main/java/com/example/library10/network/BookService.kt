package com.example.library10.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String?): BookResponse


}