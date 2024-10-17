package com.example.grupoar_tp2024.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grupoar_tp2024.recycleView.PokemonAdapter
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.ResultadoDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)

    lateinit var rvPokemones: RecyclerView
    lateinit var pokemonAdapter: PokemonAdapter
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        saludarUsuario()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        rvPokemones = findViewById(R.id.rvListaPokemones)
        pokemonAdapter = PokemonAdapter(ArrayList(), this)
        rvPokemones.adapter = pokemonAdapter

        getPokemonesPorIdEnRango(0, 100) { pokemons ->
            // Actualiza la lista en el adaptador
            pokemonAdapter.updateData(pokemons)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /* val intent = Intent(this, ::class.java)
             startActivity(intent) */
            R.id.item_BuscarPokemon -> {
                Toast.makeText(this, "Busqueda en desarrollo", Toast.LENGTH_SHORT).show()
            }

            R.id.item_Configuracion -> {
                Toast.makeText(this, "Configuración en desarrollo", Toast.LENGTH_SHORT).show()
            }

            R.id.item_AcercaDe -> {
                Toast.makeText(this, "Acerca De en desarrollo", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getPokemonesPorIdEnRango(desde: Int, hasta: Int, callback: (MutableList<PokemonDTO>) -> Unit) {
        val listaPokemon: MutableList<PokemonDTO> = ArrayList()
        val resultados = api.getPokemonPorIdEnRango(desde, hasta)

        resultados.enqueue(object : Callback<ResultadoDTO> {
            override fun onResponse(call: Call<ResultadoDTO>, response: Response<ResultadoDTO>) {
                if (response.isSuccessful) {
                    val cantidadResultados = response.body()?.results?.size
                    val requests = response.body()?.results?.map { r ->
                        api.getPokemonPorUrl(r.url)
                    }

                    // Ejecutar todas las llamadas para obtener detalles
                    requests?.forEach { request ->
                        request.enqueue(object : Callback<PokemonDTO> {
                            override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                                if (response.isSuccessful) {
                                    listaPokemon.add(response.body()!!)
                                    // Cuando se hayan cargado todos los Pokémon, notificar al callback
                                    if (listaPokemon.size == cantidadResultados) {
                                        callback(listaPokemon)
                                    }
                                } else {
                                    Log.e("API_ERROR", "Error en la llamada getPokemonPorUrl: ${response.message()}")
                                }
                            }

                            override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                                Log.e("API_ERROR", "Error en la llamada getPokemonPorUrl: ${t.message}")
                            }
                        })
                    }
                } else {
                    Log.e("API_ERROR", "Error en la llamada getPokemonPorIdEnRango: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResultadoDTO>, t: Throwable) {
                Log.e("API_ERROR", "Error en la llamada getPokemonPorIdEnRango: ${t.message}")
            }
        })
    }

    private fun saludarUsuario() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val nombreUsuario = bundle?.getString(resources.getString(R.string.nombre_usuario))
            Toast.makeText(this, "Bienvenido $nombreUsuario", Toast.LENGTH_SHORT).show()
        }


    }

}