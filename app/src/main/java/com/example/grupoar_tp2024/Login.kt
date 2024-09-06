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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var btnIngresar: Button
        lateinit var btnRegistrarme: Button
        lateinit var etEmail: EditText
        lateinit var etContrasenia: EditText
        lateinit var cbRecordar: CheckBox

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarme = findViewById(R.id.btnRegistrarme)
        etEmail = findViewById(R.id.etEmail)
        etContrasenia = findViewById(R.id.etContraseña)
        cbRecordar = findViewById(R.id.cbRecordar)

        btnIngresar.setOnClickListener{
            var email: String = etEmail.text.toString()
            if(email.isNotEmpty() && etContrasenia.text.toString().isNotEmpty()){
                if(cbRecordar.isChecked)
                    Log.i("TODO", "CheckBox Recordar email")

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Se deben completar todos los campos indicados", Toast.LENGTH_SHORT).show()
            }

        }

        btnRegistrarme.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuario::class.java))
            finish()
        }

    }
}