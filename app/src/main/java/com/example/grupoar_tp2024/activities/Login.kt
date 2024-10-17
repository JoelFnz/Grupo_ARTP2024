package com.example.grupoar_tp2024.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grupoar_tp2024.bd.AppDatabase
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import org.w3c.dom.Text
import retrofit2.*
import androidx.lifecycle.lifecycleScope
import com.example.grupoar_tp2024.apiRest.ResultadoDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        //LIBERADOR RECORDAR USUARIO
        /* val preferenciasLimpiar = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        with(preferenciasLimpiar.edit()) {
            clear()
            apply()
        } */

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Toolbar
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)
        //Layout
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnRegistrarme: Button = findViewById(R.id.btnRegistrarme)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etContrasenia: EditText = findViewById(R.id.etContraseña)
        val cbRecordar: CheckBox = findViewById(R.id.cbRecordar)
        //BD
        val baseDeDatos = AppDatabase.getDatabase(applicationContext)
        //SharedPreferences para recordar usuario
        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        val usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        val passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "")

        if (usuarioGuardado != "" && passwordGuardado != "" && usuarioGuardado != null)
            startMainActivity(usuarioGuardado)

        btnRegistrarme.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuario::class.java))
            finish()
        }

        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etContrasenia.text.toString()
            var usuarioCount = 0

            if(email.isNotEmpty() && password.isNotEmpty())
                usuarioCount = baseDeDatos.usuarioRegistradoDao().validarUsuario(email, password)

            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Se deben completar todos los campos indicados", Toast.LENGTH_SHORT).show()

            if (password.isNotEmpty() && password.length<5)
                    Toast.makeText(this,"La contraseña debe ser mayor a 5 caracteres",Toast.LENGTH_SHORT).show()

            if (usuarioCount > 0) {
                if (cbRecordar.isChecked) {
                    preferencias.edit().putString(resources.getString(R.string.nombre_usuario), email).apply()
                    preferencias.edit().putString(resources.getString(R.string.password_usuario), password).apply()
                }
                startMainActivity(email)
            } else
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startMainActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(resources.getString(R.string.nombre_usuario), email)
        startActivity(intent)
        finish()
    }

}