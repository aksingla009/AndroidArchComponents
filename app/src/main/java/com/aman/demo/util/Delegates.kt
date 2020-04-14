package com.aman.demo.util

import kotlinx.coroutines.*


/*
We will create a custom lazy block that will use the Coroutine Scope to make tha call
 */

//We will create a generic function that will take a block of suspending function
//That should be executed inside a coroutine scope
//return type is again generic type

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    //Deferred is a job with a result
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            //inside async we will invoke the block
            block.invoke(this)
        }
    }
}