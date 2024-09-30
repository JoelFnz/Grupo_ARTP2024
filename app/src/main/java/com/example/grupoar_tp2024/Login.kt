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



   //LIBERADOR RECORDAR USUARIO
        /*
   val preferenciasLimpiar = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
   with(preferenciasLimpiar.edit()) {
       clear()
       apply()
   }
*/




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

        val baseDeDatos = AppDatabase.getDatabase(applicationContext)





        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        val usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        val passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "")




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
            val email = etEmail.text.toString()
            val password = etContrasenia.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Se deben completar todos los campos indicados", Toast.LENGTH_SHORT).show()
            } else if (password.length<5){
                    Toast.makeText(this,"La contraseña debe ser mayor a 5 caracteres",Toast.LENGTH_SHORT).show()
            } else {
                val usuarioCount = baseDeDatos.usuarioRegistradoDao().validarUsuario(email, password)

                if (usuarioCount > 0) {
                    if (cbRecordar.isChecked) {
                        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                        preferencias.edit().putString(resources.getString(R.string.nombre_usuario), email).apply()
                        preferencias.edit().putString(resources.getString(R.string.password_usuario), password).apply()
                    }
                    startMainActivity(email)
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
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