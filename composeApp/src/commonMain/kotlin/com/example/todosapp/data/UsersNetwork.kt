
package com.example.todosapp.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createClient() : HttpClient{
    return HttpClient{
        install(ContentNegotiation){
            json(
                Json {
                    prettyPrint = true
                }
            )
        }
    }
}

object UsersNetwork {
    val networkClient = createClient()
}