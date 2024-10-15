package com.example.grupoar_tp2024.apiRest

import com.squareup.moshi.JsonClass
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/*
    Por el momento la clase pokemon solo contiene de atributo el forms.
    Hay que seguir agregando atributos de la api. Busquen un pokemon en la pagina y van
    a ver los atributos.

    Los nombres de los atributos no tienen que ser los mismos que los del json, pero
    hay que agregarle la etiqueta @SerializedName("nombre del atributo original") para que
    el programa no explote(!!).

    Si ven que un atributo del json es del formato atributo: {otros atributos...} es porque es un objeto.
    Creen una clase con los atributos que hay entre corchetes para que no explote(!!!).
 */

//ESTA CLASE ES LA IMPORTANTE.
@JsonClass (generateAdapter = true)
data class PokemonDTO(
    @SerializedName("forms") var forms : List<Form>
) {
    override fun toString(): String {
        return "Nombre: " + forms.get(0).name
    }
}

//CLASES COMPLEMENTARIAS
data class Form(
    val name: String,
    val url: String
)


