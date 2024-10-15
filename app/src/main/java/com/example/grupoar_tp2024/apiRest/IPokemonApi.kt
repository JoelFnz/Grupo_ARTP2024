package com.example.grupoar_tp2024.apiRest

import retrofit2.Call
import retrofit2.http.*

interface IPokemonApi {
    @GET("https://pokeapi.co/api/v2/pokemon/{nombre}")
    suspend fun getPokemonPorNombre(@Path("nombre") nombre: String) : PokemonDTO

}