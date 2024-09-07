package com.example.grupoar_tp2024

data class Pokemones(
    var id: Int,
    var nombre: String,
    var tipo: List<String>,
    var movimientos: List<String>,
    var region: String
)
