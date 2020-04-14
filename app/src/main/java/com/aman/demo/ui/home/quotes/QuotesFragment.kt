package com.aman.demo.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aman.demo.R
import com.aman.demo.util.Coroutines
import com.aman.demo.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    //We will get Factory from kodein
    private val quotesCustomFactory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, quotesCustomFactory).get(QuotesViewModel::class.java)

        //From View Model we can call the Quotes
        //Since the type of Quotes is Deferred<liveData<List<Quotes>>>
        //so we need to call await to get the LIveData from Quotes
        //Now we need to call the await from Coroutine scope or another suspend fun
        //We cant use suspend fun because we cant make onActivityCreated a suspedning function
        //So therefore use Coroutine scope
        Coroutines.main {
            val quotesLiveData = viewModel.quotes.await()
            //Await is used to call the lazyDeferred block which had async function Call
            quotesLiveData.observe(viewLifecycleOwner, Observer {
                context?.toast("Number of Quotes in API Response : ${it.size}")
            })
        }


    }
}
