package com.aman.demo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aman.demo.data.entities.Quotes
import com.aman.demo.data.entities.User

//Use Annotation @database to tell that this is a data base
// list all the entities for this data base
@Database(
    entities = [User::class, Quotes::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDAO

    abstract fun getQuoteDao(): QuoteDAO

    //companion object to create our app data base and to access it as we generally access a static variable
    companion object {

        //Volatile in kotlin means that this variable is immediately visible to all the other threads
        @Volatile
        private var instance: AppDataBase? = null

        //Use this Lock to make sure that we do not create two instances of our database
        private val LOCK = Any()

        //When we create the AppDatabase we will pass context to invoke method
        //invoke function will first check that if instance is not null then it will return us the instance
        // if instance is null then using LOCK and inside synchronised block we will build the database instance
        //But there also we wil again check if instance is not null then send the instance otherwise call our buildDataBase method
        //Elvis operator
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //Synchronised block
            instance ?: buildDataBase(context).also { instance = it }
            //We used also block
            //We will assign the returned value from buildDatabase function  that is our database actually to the instance
            //using instance = it
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "MyDataBase.db"
            ).build()

    }
}