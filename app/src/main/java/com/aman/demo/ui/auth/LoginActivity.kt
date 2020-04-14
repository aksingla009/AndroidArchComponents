package com.aman.demo.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aman.demo.R
import com.aman.demo.data.entities.User
import com.aman.demo.databinding.ActivityLoginBinding
import com.aman.demo.ui.home.HomeActivity
import com.aman.demo.util.hide
import com.aman.demo.util.show
import com.aman.demo.util.snackBar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

//Using Interface AuthListener to get callbacks from AuthViewModel into LoginActivity
class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    //We will get dependency from below kodein
    override val kodein by kodein()

    private val customFactory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
         val api = MyApiInterface(networkConnectionInterceptor)
         val db = AppDataBase(this)
         val repository = UserRepository(api, db)
         //We need to pass this repository to AuthViewMOdel but
         //we get instance of AuthViewMOdel from ViewModelProvider so to pass required params
         //we need to implement CustomViewMOdelFactory

         val customFactory = AuthViewModelFactory(repository)*/

        val dataBindingInstance: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        //We have the Binding Now
        //Now we need to get the View Model
        // val ourViewModelInstance  = ViewModelProvider(this).get(AuthViewModel::class.java)
        val ourViewModelInstance =
            ViewModelProvider(this, customFactory).get(AuthViewModel::class.java)

        //Then we will set this ViewModelInstance as Binding View Model
        dataBindingInstance.anyNameAuthViewModel = ourViewModelInstance
        //It will bind our data with the UI
        //Although in this case we are just getting the data from UI


        //We also need to define the Auth Listener to our view model
        ourViewModelInstance.authListener = this

        ourViewModelInstance.getLoggedInUser().observe(this, Observer {
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
        progress_bar.show()//progress_bar is the id from activity_login since we had data binding implementation
        //toast("Login Started")
        root_layout.snackBar("Login Started")
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        //root_layout.snackBar("${user.name} is logged in")
        //toast("${user.name} is logged in")
    }

    /*
    override fun onSuccess(loginResponseFromRepo: LiveData<String>) {
        //Here we can Observe the LoginResponse LiveData
        loginResponseFromRepo.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }

     */

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackBar(message)
        //toast(message)
    }

}
