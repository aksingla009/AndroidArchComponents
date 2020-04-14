package com.aman.demo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//We will define all the functions that we need for coroutines
// To define all the functions we will use object so that we can directly call the function using Coroutine its similar to static in java

object Coroutines {
    //Inside this object we will define a function main which will take another function as a parameter
    //that another function will be a co routine function
    //fun main(work : suspend (()-> Unit)) this function will return us a job so we will simply write = and assign that job
    fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }

    //For CoroutineScope we will first define the thread that It will use here we will use the Main thread
    //Launch returns a JOB
    // This JOB is returned by main function
    //inside the launch we will perform the work which we get inside the parameter

    fun io(work: suspend () -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        work()
    }

}