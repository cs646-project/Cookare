
package com.example.cookare.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: Long,
    val username: String,
    val avatar_url: String,
    val password: String,
    val tags: String,
    val email:String

)

/**
 * Static data
 */

val users = User(
    id = 1L,
    username = "Karina",
    avatar_url = "https://i.postimg.cc/qM4pfGc6/Snipaste-2022-07-10-16-35-59.jpg",
    password = "123",
    tags = "I eat an apple a day",
    email = "karina@gmail.com"
)
