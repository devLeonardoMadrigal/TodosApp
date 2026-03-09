package com.example.todosapp.viewmodel

import com.example.todosapp.model.UserDTO

sealed class UsersScreenState{
    data object  Nothing  : UsersScreenState()
    data object  Loading  : UsersScreenState()
    data class Error(val msg   : String) :  UsersScreenState()
    data class Success(val todos : List<UserDTO>) : UsersScreenState()
}