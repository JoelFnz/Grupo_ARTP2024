package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private val url = "https://pokeapi.co/api/v2/"
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}