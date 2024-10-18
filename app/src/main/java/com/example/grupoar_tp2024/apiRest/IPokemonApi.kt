package com.example.grupoar_tp2024.apiRest

import retrofit2.Call
import retrofit2.http.*

interface IPokemonApi {
    @GET("pokemon/{nombre}")
    fun getPokemonPorNombre(@Path("nombre") nombre: String) : Call<PokemonDTO>

    @GET("pokemon/{id}")
    fun getPokemonPorId(@Path("id") id: Long): Call<PokemonDTO>

    @GET("pokemon")
    fun getPokemonPorIdEnRango(
        @Query("offset") inicio: Int,
        @Query("limit") cantidadAObtener: Int) : Call<ResultadoDTO>

    @GET
    fun getPokemonPorUrl(@Url url: String): Call<PokemonDTO>

}