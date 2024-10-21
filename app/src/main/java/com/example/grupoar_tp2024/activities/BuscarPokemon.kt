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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuscarPokemon : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_pokemon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBuscar: Button = findViewById(R.id.buscar)
        val btnVerPokemon: Button = findViewById(R.id.irAlPokemon)
        val txtResultados: TextView = findViewById(R.id.textoResultado)
        val txtPokemonResultado: TextView = findViewById(R.id.pokemonResultado)
        val etNumero: EditText = findViewById(R.id.numero)
        val etNombre: EditText = findViewById(R.id.nombre)
        var resultadoPokemon: PokemonDTO? = null

        txtResultados.visibility = View.INVISIBLE
        btnVerPokemon.visibility = View.INVISIBLE
        btnVerPokemon.isEnabled = false

        btnBuscar.setOnClickListener {

            if (etNumero.text.toString().isNotEmpty()) {

                val idPokemon = etNumero.text.toString().toLong()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val resultadoPokemon = api.getPokemonPorId(idPokemon)

                        withContext(Dispatchers.Main) {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.VISIBLE
                            txtResultados.text = "Resultados: "
                            txtPokemonResultado.text = "Nombre: ${resultadoPokemon.name}    ID: ${resultadoPokemon.id}"
                            btnVerPokemon.visibility = View.VISIBLE
                            btnVerPokemon.isEnabled = true
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.INVISIBLE
                            txtResultados.text = "No hay coincidencias"
                            Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${e.message}")
                        }
                    }
                }
            }
            else if (etNombre.text.toString().isNotEmpty() && !etNombre.text.toString().all { it.isDigit() }) {
                val nombrePokemon = etNombre.text.toString().lowercase()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val resultadoPokemon = api.getPokemonPorNombre(nombrePokemon)

                        withContext(Dispatchers.Main) {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.VISIBLE
                            txtResultados.text = "Resultados: "
                            txtPokemonResultado.text = "Nombre: ${resultadoPokemon.name}    ID: ${resultadoPokemon.id}"
                            btnVerPokemon.visibility = View.VISIBLE
                            btnVerPokemon.isEnabled = true
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.INVISIBLE
                            txtResultados.text = "No hay coincidencias"
                            Log.e("API_ERROR", "Error en la llamada getPokemonPorNombre: ${e.message}")
                        }
                    }
                }
            } else if (etNombre.text.toString().all { it.isDigit() }) {
                txtResultados.visibility = View.VISIBLE
                txtResultados.text = "No hay coincidencias"
                txtPokemonResultado.visibility = View.INVISIBLE
            } else {
                Toast.makeText(this, "Alguno de los campos debe ser rellenado", Toast.LENGTH_SHORT).show()
            }

            btnVerPokemon.setOnClickListener {
                val intent = Intent(this, PokeDetallesActivity::class.java)

                resultadoPokemon?.let {
                    intent.putExtra("id", it.id) // Int
                    intent.putExtra("nombre", it.name)
                    intent.putExtra("tipo", it.types.toString())
                    intent.putExtra("movimientos", it.moves.toString())
                    intent.putExtra("habilidades", it.abilities.toString())
                    intent.putExtra("peso", it.weight) // Int
                    intent.putExtra("altura", it.height) // Int
                    intent.putExtra("sprites", it.sprites.toString()) // URLs como string
                    intent.putExtra("gritos", it.cries.toString()) // Lo mismo aquí
                }

                startActivity(intent)
                finish()
            }
        }


    }
}
