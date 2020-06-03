package com.danjcollins.gonopoly

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GonopolyRestAPI {
    private val gonopolyApi: GonopolyService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://us-central1-idyllic-bloom-278618.cloudfunctions.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        gonopolyApi = retrofit.create(GonopolyService::class.java)
    }
    fun retrieveProperties(playerCount: Int, cardCount: Int): Call<List<PlayerPropertySet>> {
        return gonopolyApi.retrieveProperties(playerCount, cardCount)
    }
}