package com.example.grupoar_tp2024

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class PokeDetallesActivity : AppCompatActivity() {

    lateinit var txtNombre: TextView
    lateinit var etId: TextView
    lateinit var txtTipo: TextView
    lateinit var txtMovimiento: TextView
    lateinit var txtRegion: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_poke_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Obtener los datos del Intent

        txtNombre=findViewById(R.id.txtNombre)
        etId=findViewById(R.id.etId)
        txtTipo=findViewById(R.id.txtTipo)
        txtMovimiento=findViewById(R.id.txtMovimiento)
        txtRegion=findViewById(R.id.txtRegion)

        val nombre = intent.getStringExtra("nombre")
        val id = intent.getIntExtra("id", -1)
        val tipo= intent.getStringExtra("tipo")
        val movimientos = intent.getStringExtra("movimientos")
        val region = intent.getStringExtra("region")

        txtNombre.text = nombre
        etId.text = id.toString()
        txtTipo.text = tipo
        txtMovimiento.text = movimientos
        txtRegion.text = region

    }
}