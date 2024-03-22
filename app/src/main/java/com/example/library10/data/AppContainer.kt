package com.example.library10.data

import com.example.library10.network.BookResponse
import com.example.library10.network.BookService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository:BookRepository
}

class DefaultAppContainer:AppContainer{
    val baseURL = "https://www.googleapis.com/books/v1/"
    val json = Json{
        this.ignoreUnknownKeys = true
    }
    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    val retrofitservice:BookService by lazy {
        retrofit.create(BookService::class.java)
    }
    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(retrofitservice)
    }
}