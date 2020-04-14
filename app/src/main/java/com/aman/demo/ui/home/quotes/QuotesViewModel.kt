package com.aman.demo.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.aman.demo.data.repository.QuotesRepository
import com.aman.demo.util.lazyDeferred

class QuotesViewModel(
    quotesRepository: QuotesRepository
) : ViewModel() {
    //We will Get Quotes from Quotes Repository
    //we can not call suspend function directly so use Coroutine scope

    //call this quotes from Quotes Fragment
    //it is used to achieve functionality that initialise only when needed
    //Although we could have used lazy block here but since we needed a Corotuine scope to call getQuotes function
    //We wil need to create a custom lazy block which will use Coroutine Scope to call the function

    //that is Quotes will be only initialised when needed
    val quotes by lazyDeferred {
        quotesRepository.getQuotes()
    }
    //We will create our custom LaZY block that will use the Coroutine scope to make the call
    //So inside util package will create one more kotlin class file name it Delegates


}
