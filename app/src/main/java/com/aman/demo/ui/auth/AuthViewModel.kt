package com.aman.demo.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.aman.demo.data.repository.UserRepository
import com.aman.demo.util.ApiException
import com.aman.demo.util.Coroutines
import com.aman.demo.util.NoInternetException

//ViewModel for Activity or fragment
//We can use AuthViewModel for both Login as welll as Signup because functionality is pretty much the same
class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    /*If I want to use this AuthView Model inside activity_login or activity_signup
    *Then convert those layouts to data binding layouts
    * To convert any normal layout to a data binding layout make root element as layout and inside that
    * 1st child should be data
    * 2nd child should be the actual layout
    */

    var name: String? = null

    //email and pwd variables wil be used to store the vales coming from UI
    var email: String? = null
    var password: String? = null

    var confirmPassword: String? = null

    var authListener: AuthListener? = null
    //When we click on Login button we will call onStarted  function of Auth Listener
    //which will then trigger onStarted function inside the corresponding Activity class which had implemented AuthListener interface

    fun getLoggedInUser() = repository.getUser()

    //This function will be called on click of Button
    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()


        //To check whether content is empty or not
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            //We will update the UI but how to do that Since we are in the View Model not inside the View
            //We will display the error message and return from here so that no further execution is done

            authListener?.onFailure("Invalid Email or Password")
            return
        }

        //Here again we are creating UserRepository Instance which is dependency of AuthView Model so later
        //we wil use dependency Injection here
        /*
        val loginResponseFromRepo = UserRepository().actualUserLogin(email!!,password!!)

        authListener?.onSuccess(loginResponseFromRepo)

         */

        //We can not make everything suspending so to make call to actualUserLogin function
        // We will create a Coroutine function
        // We will create this Coroutine function inside util package with class name Coroutines
        /* Coroutines.main{
             val response  = UserRepository().actualUserLogin(email!!,password!!)
             if(response.isSuccessful){
                 authListener?.onSuccess(response.body()?.user!!)
             }else{
                 authListener?.onFailure("Error Code : ${response.code()}")
             }
         }*/

        Coroutines.main {
            //Since now with introduction of SafeApiRequest we are getting AuthResponse Directly we can use accordingly

            //Since AuthResponse will be either valid or we will get APIException so place it inside the try catch block
            try {
                val authResponse = repository.actualUserLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
//                    authListener?.onSuccess(authResponse.user)
                }
                //If in case user is null
                authListener?.onFailure(authResponse.message!!)
                //This line will not be executed if valid user exists because of statement return@main
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e2: NoInternetException) {
                authListener?.onFailure(e2.message!!)
            }
        }

    }

    fun goToSignUpPage(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun GoToLoginPage(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpButtonClicked(view: View) {
        authListener?.onStarted()

        //To check whether content is empty or not
        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email ID is required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter the password")
            return
        }
        if (password != confirmPassword) {
            authListener?.onFailure("Password does not matched the confirm password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                //If in case user is null
                authListener?.onFailure(authResponse.message!!)
                //This line will not be executed if valid user exists because of statement return@main
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e2: NoInternetException) {
                authListener?.onFailure(e2.message!!)
            }
        }

    }


}