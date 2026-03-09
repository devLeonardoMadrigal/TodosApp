package com.example.todosapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform