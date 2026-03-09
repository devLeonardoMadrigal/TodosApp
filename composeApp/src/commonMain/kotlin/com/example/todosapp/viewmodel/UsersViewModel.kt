package com.example.todosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todosapp.data.UsersApiService
import com.example.todosapp.data.UsersApiServiceImpl
import com.example.todosapp.data.UsersNetwork
import com.example.todosapp.model.Response
import com.example.todosapp.model.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val apiService: UsersApiService) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersScreenState>(UsersScreenState.Nothing)
    val uiState get() = _uiState.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch (Dispatchers.IO){
            _uiState.update {
                UsersScreenState.Loading
            }

            val response = apiService.getUsers()

            when(response) {
                is Response.Error -> {
                    _uiState.update {
                        UsersScreenState.Error(response.msg)
                    }
                }
                is Response.Success<*> -> {
                    _uiState.update {
                        UsersScreenState.Success(response.result as List<UserDTO>)
                    }
                }
            }
        }
    }
}

val usersModelFactory = viewModelFactory {
    initializer {
        UsersViewModel(UsersApiServiceImpl(UsersNetwork.networkClient))
    }
}