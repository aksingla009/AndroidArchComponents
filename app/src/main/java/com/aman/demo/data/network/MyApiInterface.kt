package com.aman.demo.data.network

import com.aman.demo.data.network.responses_model.AuthResponse
import com.aman.demo.data.network.responses_model.QuotesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApiInterface {

    //To use parsed response instead of returning Call<ResponseBody> we will return Response
    //To use CoRoutine we will use suspend function which makes userLogin function as a suspending function
    //Suspending functions are the centre of everything in  co routines
    //Suspending functions are normal functions that can be paused and resumed at a later time
    //These types of functions can execute long running operations and wait for it to complete without blocking

    @FormUrlEncoded
    @POST("mvvm/login")
    suspend fun userLogin(
        @Field("email") userName: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("mvvm/signup")
    suspend fun userSignUp(
        @Field("name") name: String,
        @Field("email") userName: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("mvvm/quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    // http://www.mocky.io/v2/5e956b9e2f00006300025016
    /*
{
    "isSuccessful" : true,
    "quotes":[
        {
            "id" : 2,
            "quote" : "Don't Cry because it's over. SMILE because it happened",
            "author" : "Aman",
            "thumbnail" : "null",
            "created_at" : "2019-07-06 12:26:26",
            "updated_at" : "2019-07-06 12:26:26"
        },
        {
            "id" : 1,
            "quote" : "SMILE because it happened",
            "author" : "Raman",
            "thumbnail" : "null",
            "created_at" : "2019-07-06 12:26:26",
            "updated_at" : "2019-07-06 12:26:26"
        }

        ]

    }
     */

    companion object {
        //operator keyword is used so that you could perform operator overloading
        // directly call the invoke function using MyApiInterface()
        /*operator fun invoke(): MyApiInterface {
            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiInterface::class.java)
        }*/

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApiInterface {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiInterface::class.java)
        }
    }

    // .baseUrl("https://api.simplifiedcoding.in/course-apis/recyclerview/movies/")

/*
    @FormUrlEncoded
    @POST("login")
    fun userLogin(@Field("email")userName:String, @Field("password")password:String): Call<ResponseBody>
    // The above function will take userName and password as params and will insert in email and password field of API
    //using endpoint of login and ultimately will return the Call of type Response Body


    companion object{
        //operator keyword is used so that you could directly call the invoke function using MyApiInterface()
        operator fun invoke():MyApiInterface{
            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApiInterface::class.java)
        }
    }


    //Companion object is just like object but instead this is created within a class or interface
    //object and companion object are written so that their properties and functions could behave as a static ones
    //since kotlin does not have a static keyword object and companion objects are used to get that functionality
    // for eg for above example we can simply call invoke function as MyApiInterface.invoke()
    // or instead of that we could also even use MyApiInterface() because of operator keyword
    // and it will call the invoke function directly

 */

}