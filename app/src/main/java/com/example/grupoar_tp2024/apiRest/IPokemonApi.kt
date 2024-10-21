package com.example.grupoar_tp2024.apiRest

import retrofit2.Call
import retrofit2.http.*

interface IPokemonApi {

    @GET("pokemon/{nombre}")
    suspend fun getPokemonPorNombre(@Path("nombre") nombre: String) : PokemonDTO

    @GET("pokemon/{id}")
    suspend fun getPokemonPorId(@Path("id") id: Long): PokemonDTO

    @GET("pokemon")
    suspend fun getPokemonPorIdEnRango(
        @Query("offset") inicio: Int,
        @Query("limit") cantidadAObtener: Int) : ResultadoDTO

    @GET
    suspend fun getPokemonPorUrl(@Url url: String): PokemonDTO
}