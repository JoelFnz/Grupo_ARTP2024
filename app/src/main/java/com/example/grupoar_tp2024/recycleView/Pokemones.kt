package com.example.grupoar_tp2024.recycleView

import com.example.grupoar_tp2024.apiRest.MoveDetails
import com.example.grupoar_tp2024.apiRest.TypeSlot

data class Pokemones(
    var id: Int,
    var nombre: String,
    var tipo: List<TypeSlot>,
    var movimientos: List<MoveDetails>,
    var region: String
)
