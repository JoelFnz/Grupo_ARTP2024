package com.example.grupoar_tp2024.bd

import androidx.room.*;

@Entity(tableName = "UsuarioRegistrado")
data class UsuarioRegistrado(
    @PrimaryKey (autoGenerate = false)val correo: String,
    @ColumnInfo (name = "contraseña")val contrasenia: String
) {

}