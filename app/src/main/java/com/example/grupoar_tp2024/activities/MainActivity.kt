package com.example.grupoar_tp2024.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.ResultadoDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import com.example.grupoar_tp2024.recycleView.PokemonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        Thread {
            getPokemonesPorIdEnRango(valorInicio, 50, btnSiguiente) { pokemons ->
                runOnUiThread {
                    pokemonAdapter.updateData(pokemons)
                }
            }
        }.start()

        btnSiguiente.setOnClickListener {
            Thread {
                if (valorInicio < 1300) {
                    runOnUiThread {
                        btnSiguiente.isEnabled = false
                        if (valorInicio == 0)
                            btnAnterior.isEnabled = true
                    }

                    valorInicio += 50
                    getPokemonesPorIdEnRango(valorInicio, 50, btnSiguiente) { pokemons ->
                        runOnUiThread {
                            pokemonAdapter.updateData(pokemons)
                        }
                    }
                }
            }.start()
        }

        btnAnterior.setOnClickListener {
            Thread {
                if (valorInicio > 0) {
                    runOnUiThread {
                        btnAnterior.isEnabled = false
                        btnSiguiente.isEnabled = true
                    }

                    valorInicio -= 50

                    getPokemonesPorIdEnRango(valorInicio, 50, btnAnterior) { pokemons ->
                        runOnUiThread {
                            pokemonAdapter.updateData(pokemons)
                        }
                    }
                }
            }.start()
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
                mostrarConfiguracion(toolbar)
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
        Thread {
            try {
                val resultados = api.getPokemonPorIdEnRango(desde, hasta).execute()

                if (resultados.isSuccessful) {
                    val cantidadResultados = resultados.body()?.results?.size ?: 0
                    val requests = resultados.body()?.results?.map { r -> api.getPokemonPorUrl(r.url).execute() }

                    requests?.forEach { response ->
                        if (response.isSuccessful) {
                            listaPokemon.add(response.body()!!)
                        }
                    }


                    runOnUiThread {
                        boton.isEnabled = true
                        callback(listaPokemon)
                    }
                } else {
                    runOnUiThread {
                        boton.isEnabled = true
                        Log.e("API_ERROR", "Error en la llamada: ${resultados.message()}")
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    boton.isEnabled = true
                    Log.e("API_ERROR", "Error: ${e.message}")
                }
            }
        }.start()
    }


    private fun saludarUsuario() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val nombreUsuario = bundle?.getString(resources.getString(R.string.nombre_usuario))
            Toast.makeText(this, "Bienvenido $nombreUsuario", Toast.LENGTH_SHORT).show()
        }


    }

    private fun mostrarConfiguracion(view : View){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu_configuracion, popup.menu)
        popup.gravity = Gravity.END
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.saludar -> {
                    saludarUsuario()
                    CoroutineScope(Dispatchers.Main).launch {
                        // Si hay un MediaPlayer existente lo liberamos

                        val mediaPlayer = MediaPlayer().apply {
                            setDataSource("https://us-tuna-sounds-files.voicemod.net/4200cf0b-4c84-4df3-bd7a-0559a5feccf8-1648917759974.mp3")
                            setOnPreparedListener {
                                start()
                            }
                            setOnErrorListener { _, _, _ ->

                                false
                            }
                            prepareAsync()
                        }
                    }
                    true
                }
                R.id.cambiarTema -> {
                    cambiarAModoNoche()
                    true
                }
                R.id.cerrarSesion -> {
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
        popup.show()
    }

    private fun cambiarAModoNoche(){
        val preferencias = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if(!preferencias.getBoolean("dark_mode", false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        preferencias.edit().putBoolean("dark_mode", !preferencias.getBoolean("dark_mode", false)).apply()
        recreate()
    }

}

