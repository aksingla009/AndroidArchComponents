package com.aman.demo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Define user data class to map the values which we are getting in login response
since its a data class we can not have a body
We will define all the values that are required for our User
These variables are required to map our User to this class
 */

//Since this class is an entity for our database We will annotate it with @Entity
//tableName should be used if you want specific name of table inside the database

const val CURRENT_USER_ID = 0

@Entity(tableName = "User")
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}

