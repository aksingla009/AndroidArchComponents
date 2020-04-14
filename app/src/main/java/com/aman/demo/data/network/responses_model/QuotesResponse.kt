package com.aman.demo.data.network.responses_model

import com.aman.demo.data.entities.Quotes

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quotes>

)