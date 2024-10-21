package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TipoDTO(
    val sprites: SpriteTipo
)

data class SpriteTipo(
    @Json(name = "generation-viii") val generationVII : TipoGeneracionVII
)

data class  TipoGeneracionVII(
    @Json(name = "sword-shield") val swordAndShield: TipoSwordAndShield
)

data class TipoSwordAndShield(
    @Json(name = "name_icon") val icono: String
)
