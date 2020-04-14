package com.aman.demo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Quotes(
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,

    var quote: String? = null,
    var author: String? = null,
    var thumbnail: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
)