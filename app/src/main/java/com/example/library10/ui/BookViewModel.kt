package com.example.library10.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.library10.BookApplication
import com.example.library10.data.BookRepository
import com.example.library10.network.BookResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface _uiState{
    data class Success(val books: BookResponse):_uiState
    object Error:_uiState
    object Loading:_uiState

}

class BookViewModel( val bookRepository: BookRepository):ViewModel() {
    var uiState:_uiState by mutableStateOf(_uiState.Loading)
        private set

    fun getBooks(query: String?){
        viewModelScope.launch {
            uiState = _uiState.Loading
            uiState = try {
                _uiState.Success(bookRepository.getBooks(query))
            }catch (e: IOException) {
                _uiState.Error
            } catch (e: HttpException) {
                _uiState.Error
            }
        }
    }

    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = ( this[APPLICATION_KEY] as BookApplication)
                val repository = application.container.bookRepository
                BookViewModel(repository)
            }
        }
    }
}

