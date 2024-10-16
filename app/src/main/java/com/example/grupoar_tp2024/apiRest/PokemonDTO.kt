package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.JsonClass
import com.google.gson.annotations.SerializedName

//ESTA CLASE ES LA IMPORTANTE.
@JsonClass (generateAdapter = true)
data class PokemonDTO(
    val name: String,
    val id: Int,
    val weight: Int,
    val height: Int,
    val types: List<TypeSlot>,
    val sprites: Sprite,
    val cries: Cry,
    val moves: List<MoveDetails>,
    val abilities: List<AbilityDetails>
)

//CLASES COMPLEMENTARIAS
data class Type(
    val name: String,
    val url: String
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Ability(
    val name: String,
    val url: String
)

data class AbilityDetails(
    val ability: Ability,
    val is_hidden: Boolean,
    val slot: Int
)

data class Cry(
    //Son urls
    val latest: String,
    val legacy: String
)

data class Move(
    val name: String,
    val url: String
)

data class MoveDetails(
    val move: Move
)

data class Sprite(
    //Todos son urls a imagenes
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)



