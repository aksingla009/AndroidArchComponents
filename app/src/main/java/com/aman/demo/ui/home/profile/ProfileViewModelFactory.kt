package com.aman.demo.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aman.demo.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repo: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //return super.create(modelClass)
        return ProfileViewModel(repo) as T
    }

}