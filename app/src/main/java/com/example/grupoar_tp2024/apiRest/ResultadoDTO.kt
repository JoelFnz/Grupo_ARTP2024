package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultadoDTO(
    val count: Int,
    val results: List<Resultado>
)

data class Resultado(
    val name: String,
    val url: String
)