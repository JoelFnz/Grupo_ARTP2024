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

        btnBuscar.setOnClickListener{
            if(etNumero.text.toString().isNotEmpty()){
                val callPokemon = api.getPokemonPorId(etNumero.text.toString().toLong())
                callPokemon.enqueue(object : Callback<PokemonDTO> {
                    override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                        if(response.isSuccessful){
                            resultadoPokemon = response.body()
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.VISIBLE
                            txtResultados.text = "Resultados: "
                            txtPokemonResultado.text = "Nombre: ${resultadoPokemon?.name}    ID: ${resultadoPokemon?.id}"
                            btnVerPokemon.visibility = View.VISIBLE
                            btnVerPokemon.isEnabled = true
                        } else {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.INVISIBLE
                            txtResultados.text = "No hay coincidencias"
                            Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                        txtResultados.visibility = View.VISIBLE
                        txtPokemonResultado.visibility = View.INVISIBLE
                        txtResultados.text = "No hay coincidencias"
                        Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${t.message}")
                    }

                })
            }
            else if(etNombre.text.toString().isNotEmpty() && !etNombre.text.toString().all{ it.isDigit() }){
                val callPokemon = api.getPokemonPorNombre(etNombre.text.toString().lowercase())
                callPokemon.enqueue(object : Callback<PokemonDTO> {
                    override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                        if(response.isSuccessful){
                            resultadoPokemon = response.body()
                            txtPokemonResultado.visibility = View.VISIBLE
                            txtResultados.visibility = View.VISIBLE
                            txtResultados.text = "Resultados: "
                            txtPokemonResultado.text = "Nombre: ${resultadoPokemon?.name}    ID: ${resultadoPokemon?.id}"
                            btnVerPokemon.visibility = View.VISIBLE
                            btnVerPokemon.isEnabled = true
                        } else {
                            txtResultados.visibility = View.VISIBLE
                            txtPokemonResultado.visibility = View.INVISIBLE
                            txtResultados.text = "No hay coincidencias"
                            Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                        txtResultados.visibility = View.VISIBLE
                        txtPokemonResultado.visibility = View.INVISIBLE
                        txtResultados.text = "No hay coincidencias"
                        Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${t.message}")
                    }

                })
            }
            else if(etNombre.text.toString().all{ it.isDigit() }){
                txtResultados.visibility = View.VISIBLE
                txtResultados.text = "No hay coincidencias"
                txtPokemonResultado.visibility = View.INVISIBLE
            }
            else
                Toast.makeText(this, "Alguno de los campos debe ser rellenado", Toast.LENGTH_SHORT).show()

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
                } ?: run {
                    Toast.makeText(this, "Primero busca un Pokémon antes de ver detalles", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}
