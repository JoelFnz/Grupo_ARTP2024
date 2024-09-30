package com.example.grupoar_tp2024

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var btnIngresar: Button
        lateinit var btnRegistrarme: Button
        lateinit var etEmail: EditText
        lateinit var etContrasenia: EditText
        lateinit var cbRecordar: CheckBox

        lateinit var toolbar: Toolbar

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)

        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarme = findViewById(R.id.btnRegistrarme)
        etEmail = findViewById(R.id.etEmail)
        etContrasenia = findViewById(R.id.etContraseña)
        cbRecordar = findViewById(R.id.cbRecordar)

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales),  MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "")

        if (usuarioGuardado != "" && passwordGuardado != "") {
            if (usuarioGuardado != null) {
                startMainActivity(usuarioGuardado)
            }

        }

        btnRegistrarme.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuario::class.java))
            finish()
        }

        btnIngresar.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etContrasenia.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Se deben completar todos los campos indicados", Toast.LENGTH_SHORT).show()
            } else {
                if (cbRecordar.isChecked) {
                    var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                    preferencias.edit().putString(resources.getString(R.string.nombre_usuario), email).apply()
                    preferencias.edit().putString(resources.getString(R.string.password_usuario), password).apply()
                }
                startMainActivity(email)
            }
        }



    }

    private fun startMainActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(resources.getString(R.string.nombre_usuario), email)
        startActivity(intent)
        finish()
    }
}