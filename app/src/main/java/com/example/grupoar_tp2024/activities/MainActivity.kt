package com.example.grupoar_tp2024.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
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

        val btnSiguiente: Button = findViewById(R.id.siguiente)
        val btnAnterior: Button = findViewById(R.id.anterior)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        rvPokemones = findViewById(R.id.rvListaPokemones)
        pokemonAdapter = PokemonAdapter(ArrayList(), this)
        rvPokemones.adapter = pokemonAdapter

        btnSiguiente.isEnabled =  false
        btnAnterior.isEnabled = true

        var valorInicio = 0

        getPokemonesPorIdEnRango(valorInicio, 50, btnSiguiente) { pokemons ->
            // Actualiza la lista en el adaptador
            pokemonAdapter.updateData(pokemons)
        }

        btnSiguiente.setOnClickListener {
            if (valorInicio < 1300) {
                btnSiguiente.isEnabled = false
                if (valorInicio == 0)
                    btnAnterior.isEnabled = true
                valorInicio += 50
                getPokemonesPorIdEnRango(valorInicio, 50, btnSiguiente) { pokemons ->
                    pokemonAdapter.updateData(pokemons)
                }
            }
        }

        btnAnterior.setOnClickListener{
            if(valorInicio > 0){
                btnAnterior.isEnabled = false
                valorInicio -= 50
                btnSiguiente.isEnabled = true
                getPokemonesPorIdEnRango(valorInicio, 50, btnAnterior) { pokemons ->
                    pokemonAdapter.updateData(pokemons)
                }
            }
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
                val intent = Intent(this, BuscarPokemon::class.java)
                startActivity(intent)
            }

            R.id.item_Configuracion -> {
                showPopupMenu(findViewById(R.id.toolbar))
            }

            R.id.item_AcercaDe -> {

               val intent = Intent(this, AcercaDe::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getPokemonesPorIdEnRango(desde: Int, hasta: Int, boton: Button, callback: (MutableList<PokemonDTO>) -> Unit) {
        val listaPokemon: MutableList<PokemonDTO> = ArrayList()
        val resultados = api.getPokemonPorIdEnRango(desde, hasta)

        resultados.enqueue(object : Callback<ResultadoDTO> {
            override fun onResponse(call: Call<ResultadoDTO>, response: Response<ResultadoDTO>) {
                if (response.isSuccessful) {
                    val cantidadResultados = response.body()?.results?.size
                    val requests = response.body()?.results?.map { r ->
                        api.getPokemonPorUrl(r.url)
                    }

                    requests?.forEach { request ->
                        request.enqueue(object : Callback<PokemonDTO> {
                            override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                                if (response.isSuccessful) {
                                    listaPokemon.add(response.body()!!)
                                    // Cuando se hayan cargado todos los Pokémon, notificar al callback
                                    if (listaPokemon.size == cantidadResultados) {
                                        boton.isEnabled = true
                                        callback(listaPokemon)
                                    }
                                } else {
                                    Log.e("API_ERROR", "Error en la llamada getPokemonPorUrl: ${response.message()}")
                                }
                            }

                            override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                                boton.isEnabled = true
                                Log.e("API_ERROR", "Error en la llamada getPokemonPorUrl: ${t.message}")
                            }
                        })
                    }
                } else {
                    boton.isEnabled = true
                    Log.e("API_ERROR", "Error en la llamada getPokemonPorIdEnRango: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResultadoDTO>, t: Throwable) {
                boton.isEnabled = true
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

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_lateral, popupMenu.menu)
        popupMenu.gravity = Gravity.END

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_item1 -> {
                    Toast.makeText(this, "Seleccionaste Item 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_item2 -> {
                    Toast.makeText(this, "Seleccionaste Item 2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.itemCierreSesion -> {
                    Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
                    //LIBERADOR RECORDAR USUARIO

                    val preferenciasLimpiar = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                    with(preferenciasLimpiar.edit()) {
                        clear()
                        apply()
                    }
                    startActivity(Intent(this, Login::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

}

