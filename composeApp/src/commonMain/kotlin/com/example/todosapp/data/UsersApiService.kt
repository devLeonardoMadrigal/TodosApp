package com.example.todosapp.data

import com.example.todosapp.model.Response
import com.example.todosapp.model.UserDTO

interface UsersApiService {
    suspend fun getUsers() : Response<List<UserDTO>>
    suspend fun getUser(userId: String) : Response<UserDTO?>
}