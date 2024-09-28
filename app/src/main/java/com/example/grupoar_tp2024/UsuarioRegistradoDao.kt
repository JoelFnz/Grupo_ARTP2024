package com.example.grupoar_tp2024

import androidx.room.*;
@Dao
interface UsuarioRegistradoDao {
    @Query("SELECT * FROM UsuarioRegistrado")
    fun getAll(): List<UsuarioRegistrado>

    @Query("SELECT COUNT(*) FROM UsuarioRegistrado WHERE UsuarioRegistrado.correo = :correo")
    fun existeUsuario(correo: String): Int;

    @Query("SELECT COUNT(*) FROM UsuarioRegistrado WHERE UsuarioRegistrado.correo = :correo " +
            "AND UsuarioRegistrado.contraseña = :contrasenia")
    fun validarUsuario(correo: String, contrasenia: String): Int;

    @Insert
    fun insert(usuario: UsuarioRegistrado)
}