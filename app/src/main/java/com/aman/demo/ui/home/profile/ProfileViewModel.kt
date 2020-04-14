package com.aman.demo.ui.home.profile

import androidx.lifecycle.ViewModel
import com.aman.demo.data.repository.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()
    //We will bind user's data in profile_fragment using ViewModel

}
