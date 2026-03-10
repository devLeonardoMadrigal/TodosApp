package com.example.todosapp.data

import com.example.todosapp.model.Response
import com.example.todosapp.model.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType


const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class UsersApiServiceImpl(private val client: HttpClient) : UsersApiService {

    override suspend fun getUsers(): Response<List<UserDTO>> {
      return try {
            val users = client.get("${BASE_URL}users"){
                accept(ContentType.Application.Json)
            }
            Response.Success(users.body())
        }catch (e: Exception){
            Response.Error(e.message.toString())
        }
    }

    override suspend fun getUser(userId: String): Response<UserDTO> {
        return try {
            val user = client.get("${BASE_URL}users/${userId}"){
                accept(ContentType.Application.Json)
            }
            Response.Success(user.body())
        }catch (e: Exception){
            Response.Error(e.message.toString())
        }
    }
}

