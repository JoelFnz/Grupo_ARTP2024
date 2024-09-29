package com.example.grupoar_tp2024

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrarUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etMail = findViewById<EditText>(R.id.mailTextEdit)
        val etPassword = findViewById<EditText>(R.id.passTextEdit)
        val etPasswordAgain = findViewById<EditText>(R.id.pass2TextEdit)
        val cbAgreement = findViewById<CheckBox>(R.id.checkBox)
        val btnRegister = findViewById<Button>(R.id.singUpButton)
        val baseDeDatos = AppDatabase.getDatabase(applicationContext)

        lateinit var toolbar: Toolbar

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)

        btnRegister.setOnClickListener {
            val mail = etMail.text.toString()
            val password = etPassword.text.toString()
            val passwordAgain = etPasswordAgain.text.toString()
            val isAgreed = cbAgreement.isChecked

            if(mail.isEmpty() || password.isEmpty() || passwordAgain.isEmpty() || !isAgreed){
                //Si algún campo está vacío entramos acá
                Toast.makeText(
                    this,
                    "Por favor, llena todos los campos correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(baseDeDatos.usuarioRegistradoDao().existeUsuario(mail) > 0){
                //Si existe un usuario con el mail ingresado entramos acá
                Toast.makeText(this,
                    "Ya existe un usuario registrado que utiliza ese correo electrónico",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(password.length < 5){
                Toast.makeText(this,
                    "El largo de la contraseña debe ser mayor a 5 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                //Si no hay ningún problema hacemos el insert
                baseDeDatos.usuarioRegistradoDao().insert(UsuarioRegistrado(mail, password))
                Toast.makeText(
                    this,
                    "Registro exitoso", Toast.LENGTH_SHORT
                ).show()
                val intent = Intent (this, Login::class.java)
                startActivity (intent)
            }

        }

    }
}