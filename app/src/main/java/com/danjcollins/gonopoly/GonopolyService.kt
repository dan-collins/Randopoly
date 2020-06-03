package com.danjcollins.gonopoly
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GonopolyService {
    @GET("Randomize")
    fun retrieveProperties(@Query("playerCount") playerCount: Int, @Query("cardCount") cardCount: Int): Call<List<PlayerPropertySet>>
}