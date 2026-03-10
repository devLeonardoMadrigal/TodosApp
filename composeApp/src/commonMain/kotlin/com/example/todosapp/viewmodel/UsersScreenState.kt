package com.example.todosapp.viewmodel

import com.example.todosapp.model.UserDTO

data class UsersScreenState(
    val isLoading: Boolean = false,
    val users : List<UserDTO> = emptyList(),
    val error: String? = ""
)

data class SearchState(
    val isLoading: Boolean = false,
    val user : UserDTO? = null,
    val error: String? = ""
)

sealed class UsersIntent{
    object FetchData: UsersIntent()
    object Error : UsersIntent()
}

sealed class SearchUserIntent(){
    object FetchData: SearchUserIntent()
    object Error : SearchUserIntent()
}