package com.coroutinedispatcher.yourmove.api

import com.coroutinedispatcher.yourmove.model.Card
import retrofit2.Response
import retrofit2.http.GET

interface YuGiOhApi {
    @GET("randomcard.php")
    suspend fun getRandomCard(): Response<List<Card>>
}