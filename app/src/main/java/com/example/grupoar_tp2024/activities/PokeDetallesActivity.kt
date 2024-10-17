package com.example.grupoar_tp2024.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grupoar_tp2024.R


class PokeDetallesActivity : AppCompatActivity() {

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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)

        val txtNombre: TextView = findViewById(R.id.txtNombre)
        val etId: TextView = findViewById(R.id.etId)
        val txtTipo: TextView = findViewById(R.id.txtTipo)
        val txtMovimiento: TextView = findViewById(R.id.txtMovimiento)
        val txtHabilidades: TextView = findViewById(R.id.txtHabilidades)

        txtNombre.text = intent.getStringExtra("nombre")
        etId.text = "ID: ${intent.getIntExtra("id", -1)}"
        txtTipo.text = intent.getStringExtra("tipo")
        txtMovimiento.text = intent.getStringExtra("movimientos")
        txtHabilidades.text = intent.getStringExtra("habilidades")
        //txtRegion.text = region

    }
}