package com.example.todosapp.viewmodel

import com.example.todosapp.model.UserDTO

data class UsersScreenState(
    val isLoading: Boolean = false,
    val users : List<UserDTO> = emptyList(),
    val error: String? = ""
)

public sealed class UsersIntent{
    object FetchData: UsersIntent()
    object Error : UsersIntent()
}