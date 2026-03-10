package com.example.todosapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todosapp.data.UsersApiService
import com.example.todosapp.data.UsersApiServiceImpl
import com.example.todosapp.data.UsersNetwork
import com.example.todosapp.model.Response
import kotlinx.coroutines.launch

class UsersViewModel(private val apiService: UsersApiService) : ViewModel() {


    var state by mutableStateOf(UsersScreenState())

    fun processIntent(intent: UsersIntent) {
        when (intent) {
            UsersIntent.Error -> errorHandler()
            UsersIntent.FetchData -> fetchData()
        }
    }

    private fun fetchData() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {

            val response = apiService.getUsers()

            when (response) {
                is Response.Error -> {
                    state.copy(
                        isLoading = false,
                        error = response.msg
                    )
                }

                is Response.Success -> {
                    state = state.copy(
                        error = "",
                        isLoading = false,
                        users = response.result
                    )
                }
            }
        }
    }

    private fun errorHandler() {
        state = state.copy(
            error = "ERROR",
            isLoading = false,
            users = emptyList()
        )
    }
}

val usersModelFactory = viewModelFactory {
    initializer {
        UsersViewModel(UsersApiServiceImpl(UsersNetwork.networkClient))
    }
}