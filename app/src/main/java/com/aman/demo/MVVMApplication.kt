package com.aman.demo

import android.app.Application
import com.aman.demo.data.db.AppDataBase
import com.aman.demo.data.network.MyApiInterface
import com.aman.demo.data.network.NetworkConnectionInterceptor
import com.aman.demo.data.preferences.PreferenceProvider
import com.aman.demo.data.repository.QuotesRepository
import com.aman.demo.data.repository.UserRepository
import com.aman.demo.ui.auth.AuthViewModelFactory
import com.aman.demo.ui.home.profile.ProfileViewModelFactory
import com.aman.demo.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/*
Dependencies which were defined in LoginActivity

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApiInterface(networkConnectionInterceptor)
        val db = AppDataBase(this)
        val repository = UserRepository(api, db)
        val customFactory = AuthViewModelFactory(repository)

 */
class MVVMApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        //We will now bind all the objects that we need

        //to bind NetworkConnectionInterceptor
        bind() from singleton {
            NetworkConnectionInterceptor(instance())
        }

        bind() from singleton {
            MyApiInterface(instance())
        }

        bind() from singleton {
            AppDataBase(instance())
        }

        bind() from singleton {
            UserRepository(instance(), instance())
        }

        //For AuthViewMOdel Factory we dont need Singletin so use provider instead of singleton
        bind() from provider {
            AuthViewModelFactory(instance())
        }

        bind() from provider {
            ProfileViewModelFactory(instance())
        }

        bind() from singleton {
            PreferenceProvider(instance())
        }

        bind() from singleton {
            QuotesRepository(instance(), instance(), instance())
        }

        bind() from provider {
            QuotesViewModelFactory(instance())
        }
    }

}