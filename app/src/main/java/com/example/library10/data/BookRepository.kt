package com.example.library10.data

import com.example.library10.network.BookResponse
import com.example.library10.network.BookService
import retrofit2.Call

interface BookRepository {
    suspend fun getBooks(query: String?): BookResponse
}

class NetworkBookRepository(private val bookService: BookService) : BookRepository {
    override suspend fun getBooks(query: String?): BookResponse {
        return bookService.getBooks(query)
    }
}