package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.JsonClass

//ESTA CLASE ES LA IMPORTANTE.
@JsonClass (generateAdapter = true)
data class PokemonDTO(
    var name: String,
    val id: Int,
    val weight: Int,
    val height: Int,
    val types: List<TypeSlot>,
    val sprites: Sprite,
    val cries: Cry,
    val moves: List<MoveDetails>,
    val abilities: List<AbilityDetails>
){

    override fun toString(): String {
        var tipos = ""
        for(typeSlot in types)
            tipos += "$typeSlot "

        var movimientos = ""
        for(moveDetail in moves)
            movimientos += "$moveDetail "

        var habilidades = ""
        for(abilityDetail in abilities)
            habilidades += "$abilityDetail "

        return """
                "Nombre: $name, " +
                "ID: $id, " +
                "Peso: $weight, " +
                "Altura: $height, " +
                "Tipo: $tipos, " +
                "Movimientos: $movimientos, " +
                "Habilidades: $habilidades"
        """.trimIndent()
    }

}

//CLASES COMPLEMENTARIAS
data class Type(
    val name: String,
    val url: String
) {
    override fun toString(): String {
        return name
    }
}

data class TypeSlot(
    val slot: Int,
    val type: Type
) {
    override fun toString(): String {
        return type.toString()
    }
}

data class Ability(
    val name: String,
    val url: String
){
    override fun toString(): String {
        return name.replaceFirstChar { it.uppercaseChar() }
    }
}

data class AbilityDetails(
    val ability: Ability,
    val is_hidden: Boolean,
    val slot: Int
){
    override fun toString(): String {
        var retorno = "" // Habilidad
        if(is_hidden)
            retorno += "(Oculta) " // oculta

        return retorno + ability
    }
}

data class Cry(
    //Son urls
    val latest: String?,
    val legacy: String?
) {
    override fun toString(): String {
        return "$latest, $legacy"
    }
}

data class Move(
    val name: String,
    val url: String
) {
    override fun toString(): String {
        return name.replaceFirstChar { it.uppercaseChar() }
    }
}

data class MoveDetails(
    val move: Move
) {
    override fun toString(): String {
        return move.toString()  // Solo el nombre del movimiento
    }
}

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
) {
    override fun toString(): String {
        return "$back_default, " +
                "$back_female, " +
                "$back_shiny, " +
                "$back_shiny_female, " +
                "$front_default, " +
                "$front_female, " +
                "$front_shiny, " +
                "$front_shiny_female"
    }
}



