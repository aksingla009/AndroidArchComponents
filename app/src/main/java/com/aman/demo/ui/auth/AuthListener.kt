package com.aman.demo.ui.auth

import com.aman.demo.data.entities.User

interface AuthListener {
    fun onStarted()

    //fun onSuccess(loginResponseFromRepo: LiveData<String>)
    fun onSuccess(user: User)
    fun onFailure(message: String)
}