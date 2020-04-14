package com.aman.demo.data.network.responses_model

import com.aman.demo.data.entities.User

//data class which will be used to parse JSON response to Kotlin objects
//i,e data class to store the JSON response
data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)