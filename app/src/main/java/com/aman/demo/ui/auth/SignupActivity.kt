package com.aman.demo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aman.demo.R
import com.aman.demo.data.entities.User
import com.aman.demo.databinding.ActivitySignupBinding
import com.aman.demo.ui.home.HomeActivity
import com.aman.demo.util.hide
import com.aman.demo.util.show
import com.aman.demo.util.snackBar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()

    private val customFactory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val dataBindingInstance: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val myViewModelInstance: AuthViewModel =
            ViewModelProvider(this, customFactory).get(AuthViewModel::class.java)
        dataBindingInstance.newName = myViewModelInstance

        myViewModelInstance.authListener = this

        myViewModelInstance.getLoggedInUser().observe(this, Observer {
            //gives us a User
                user ->
            if (user != null) {
                //if login is successful we will go to HomeActivity
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
        root_layout.snackBar("Signup Started")
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackBar(message)
    }
}
