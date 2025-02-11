package com.ruben.room_database.network

import com.ruben.room_database.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET(".json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}
