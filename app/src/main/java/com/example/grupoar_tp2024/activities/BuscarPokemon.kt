package com.example.grupoar_tp2024.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuscarPokemon : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)
    private var resultadoPokemon: PokemonDTO? = null // Propiedad de clase

    // Declarar las variables como propiedades de la clase
    private lateinit var txtResultados: TextView
    private lateinit var txtPokemonResultado: TextView
    private lateinit var btnVerPokemon: Button
    private lateinit var btnBuscar: Button // Agregar esta línea
    private lateinit var etNumero: EditText
    private lateinit var etNombre: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_pokemon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar las variables
        txtResultados = findViewById(R.id.textoResultado)
        txtPokemonResultado = findViewById(R.id.pokemonResultado)
        btnVerPokemon = findViewById(R.id.irAlPokemon)
        btnBuscar = findViewById(R.id.buscar) // Inicializa btnBuscar
        etNumero = findViewById(R.id.numero)
        etNombre = findViewById(R.id.nombre)

        txtResultados.visibility = View.INVISIBLE
        btnVerPokemon.visibility = View.INVISIBLE
        btnVerPokemon.isEnabled = false

        btnBuscar.setOnClickListener {
            realizarBusqueda()
        }

        btnVerPokemon.setOnClickListener {
            resultadoPokemon?.let {
                val intent = Intent(this, PokeDetallesActivity::class.java)
                intent.putExtra("id", it.id)
                intent.putExtra("nombre", it.name)
                intent.putExtra("tipo", it.types.toString())
                intent.putExtra("movimientos", it.moves.toString())
                intent.putExtra("habilidades", it.abilities.toString())
                intent.putExtra("peso", it.weight)
                intent.putExtra("altura", it.height)
                intent.putExtra("sprites", it.sprites.toString())
                intent.putExtra("gritos", it.cries.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun realizarBusqueda() {
        if (etNumero.text.toString().isNotEmpty()) {
            val idPokemon = etNumero.text.toString().toLong()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    resultadoPokemon = api.getPokemonPorId(idPokemon)

                    withContext(Dispatchers.Main) {
                        mostrarResultado(resultadoPokemon)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        mostrarError("No hay coincidencias")
                        Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${e.message}")
                    }
                }
            }
        } else if (etNombre.text.toString().isNotEmpty() && !etNombre.text.toString().all { it.isDigit() }) {
            val nombrePokemon = etNombre.text.toString().lowercase()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    resultadoPokemon = api.getPokemonPorNombre(nombrePokemon)

                    withContext(Dispatchers.Main) {
                        mostrarResultado(resultadoPokemon)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        mostrarError("No hay coincidencias")
                        Log.e("API_ERROR", "Error en la llamada getPokemonPorNombre: ${e.message}")
                    }
                }
            }
        } else if (etNombre.text.toString().all { it.isDigit() }) {
            mostrarError("No hay coincidencias")
        } else {
            Toast.makeText(this, "Alguno de los campos debe ser rellenado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarResultado(pokemon: PokemonDTO?) {
        if (pokemon != null) {
            txtResultados.visibility = View.VISIBLE
            txtPokemonResultado.visibility = View.VISIBLE
            txtResultados.text = "Resultados: "
            txtPokemonResultado.text = "Nombre: ${pokemon.name.replaceFirstChar { it.uppercase() }}    ID: ${pokemon.id}"
            btnVerPokemon.visibility = View.VISIBLE
            btnVerPokemon.isEnabled = true
        }
    }

    private fun mostrarError(mensaje: String) {
        txtResultados.visibility = View.VISIBLE
        txtPokemonResultado.visibility = View.INVISIBLE
        txtResultados.text = mensaje
        btnVerPokemon.visibility = View.INVISIBLE
    }
}