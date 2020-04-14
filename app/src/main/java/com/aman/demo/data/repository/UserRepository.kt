package com.aman.demo.data.repository

import com.aman.demo.data.db.AppDataBase
import com.aman.demo.data.entities.User
import com.aman.demo.data.network.MyApiInterface
import com.aman.demo.data.network.SafeApiRequest
import com.aman.demo.data.network.responses_model.AuthResponse

//We created a param of MyApiInterface to help in dependency injection
class UserRepository(
    private val apiInterface: MyApiInterface,
    private val db: AppDataBase
) : SafeApiRequest() {

    suspend fun actualUserLogin(email: String, paswd: String): AuthResponse {
        return apiRequest { apiInterface.userLogin(email, paswd) }
    }

    suspend fun userSignUp(name: String, email: String, paswd: String): AuthResponse {
        return apiRequest { apiInterface.userSignUp(name, email, paswd) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().insert(user)

    fun getUser() = db.getUserDao().getUser()

    /* suspend fun actualUserLogin(email:String,paswd : String):Response<AuthResponse>{
         return MyApiInterface().userLogin(email,paswd)
     }*/


    /*

    //call this actualUserLogin function from AuthViewModel only
    fun actualUserLogin(email:String,paswd : String):LiveData<String>{
        val loginResponse = MutableLiveData<String>()
        //In this variable we will store the response which we will get from API Call

        //To call API we will use MyApiInterface.invoke function and then call the function userLogin and get the response in here

       // MyApiInterface.invoke().userLogin(email,paswd)
        //is same as
        //MyApiInterface().userLogin(email,paswd)
        //we have called the invoke function directly here

        //Here we are showing that userRepository have direct dependency on MyApiInterface() its a wrong practise
        //instead we should do the dependency injection which we will do later for this

        MyApiInterface.invoke().userLogin(email,paswd).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    loginResponse.value = response.body()?.string()
                }else{
                    loginResponse.value = response.errorBody()?.string()
                }
            }

        })
        return loginResponse
    }
     */
}