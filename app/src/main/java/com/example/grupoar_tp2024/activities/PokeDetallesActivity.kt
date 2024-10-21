package com.example.grupoar_tp2024.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback as Cb
import retrofit2.Response


class PokeDetallesActivity : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)
    private var mediaPlayer: MediaPlayer? = null


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
        supportActionBar?.title = ""



        val txtNombre: TextView = findViewById(R.id.txtNombre)
        val etId: TextView = findViewById(R.id.etId)
        val txtTipo: TextView = findViewById(R.id.txtTipo)
        val txtMovimiento: TextView = findViewById(R.id.txtMovimiento)
        val txtHabilidades: TextView = findViewById(R.id.txtHabilidades)
        val btnSiguiente: Button = findViewById(R.id.siguiente)
        val btnAnterior: Button = findViewById(R.id.anterior)
        val btnSpriteFem: Button = findViewById(R.id.spriteFem)
        val btnSpriteShiny: Button = findViewById(R.id.spriteShiny)
        val txtPeso: TextView = findViewById(R.id.peso)
        val txtAltura: TextView = findViewById(R.id.altura)
        val btnSonido: Button = findViewById(R.id.sonido)

        var movimientos = intent.getStringExtra("movimientos")?.replaceFirstChar { it.uppercase() }
        var habilidades = intent.getStringExtra("habilidades")?.replaceFirstChar { it.uppercase() }
        var tipos = intent.getStringExtra("tipo")?.replaceFirstChar { it.uppercase() }

        if (tipos != null) {
            tipos = tipos.replace("[", "")
            tipos = tipos.replace("]", "")
        }

        if(habilidades != null){
            habilidades = habilidades.replace("[", "")
            habilidades = habilidades.replace("]", "")
        }

        if(movimientos != null){
            movimientos =movimientos.replace("[", "")
            movimientos = movimientos.replace("]", "")
        }

        txtNombre.text = intent.getStringExtra("nombre")?.replaceFirstChar { it.uppercase() }
        etId.text = "ID: ${intent.getIntExtra("id", -1)}"
        txtTipo.text = tipos
        txtMovimiento.text = movimientos
        txtHabilidades.text = habilidades
        txtPeso.text = txtPeso.text.toString() + (intent.getIntExtra("peso", -1).toFloat() / 10) + " kg"
        txtAltura.text = txtAltura.text.toString() + (intent.getIntExtra("altura", -1).toFloat() / 10) + " m"

        val imgFront: ImageView = findViewById(R.id.img_pokemon_sprite_front)
        val imgBack: ImageView = findViewById(R.id.img_pokemon_sprite_back)
        val sprites = intent.getStringExtra("sprites")

        if(intent.getIntExtra("id", -1) > 1){
            btnAnterior.isEnabled = true
        }

        if(intent.getIntExtra("id", -1) < 10277){
            btnSiguiente.isEnabled = true
        }

        val spriteUrls = sprites?.split(",")?.map { it.trim() }
        var esMasculino = true
        var esShiny = false
        val gritosUrls = intent.getStringExtra("gritos")?.split(",")?.map { it.trim() }

        if(spriteUrls != null){
            cambiarSprite(1, imgFront, imgBack, spriteUrls)
        }

        btnSpriteFem.setOnClickListener{
            if(spriteUrls != null){
                if((!esMasculino || spriteUrls[1] == "null") && !esShiny){ //Default masc

                    cambiarSprite(1, imgFront, imgBack, spriteUrls)
                    esMasculino = true
                } else if((!esMasculino || spriteUrls[1] == "null") && esShiny){ //Shiny masc

                    cambiarSprite(3, imgFront, imgBack, spriteUrls)
                    esMasculino = true
                } else if(!esShiny){ //Default fem

                    cambiarSprite(2, imgFront, imgBack, spriteUrls)
                    esMasculino = false
                } else { //Shiny fem

                    cambiarSprite(4, imgFront, imgBack, spriteUrls)
                    esMasculino = false
                }
            }
        }

        btnSpriteShiny.setOnClickListener{
            if(spriteUrls != null){
                if(esMasculino && !esShiny){  //Default masc

                    cambiarSprite(3, imgFront, imgBack, spriteUrls)
                    esShiny = true
                } else if(!esMasculino && !esShiny){ //Default fem

                    cambiarSprite(4, imgFront, imgBack, spriteUrls)
                    esShiny = true
                } else if(!esMasculino){ //Shiny fem

                    cambiarSprite(2, imgFront, imgBack, spriteUrls)
                    esShiny = false
                } else { //Shiny masc

                    cambiarSprite(1, imgFront, imgBack, spriteUrls)
                    esShiny = false
                }
            }
        }

        btnSonido.setOnClickListener{
            if(!gritosUrls.isNullOrEmpty())
                reproducirGrito(gritosUrls[0])
        }

        btnSiguiente.setOnClickListener{
            btnSiguiente.isEnabled = false
            getPokemon(intent.getIntExtra("id", -1) + 1, btnSiguiente)
        }

        btnAnterior.setOnClickListener{
            btnAnterior.isEnabled = false
            getPokemon(intent.getIntExtra("id", -1) - 1, btnAnterior)
        }


    }

    private fun getPokemon(id: Int, boton: Button) {
        var idActual = id

        if (idActual < 1 || idActual > 10277) {
            boton.isEnabled = true
            return
        }

        if (idActual == 1026) {
            idActual = 10001
        }

        val intent = Intent(this, PokeDetallesActivity::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemon = api.getPokemonPorId(idActual.toLong())

                withContext(Dispatchers.Main) {
                    intent.putExtra("id", pokemon.id) // Int
                    intent.putExtra("nombre", pokemon.name)
                    intent.putExtra("tipo", pokemon.types.toString())
                    intent.putExtra("movimientos", pokemon.moves.toString())
                    intent.putExtra("habilidades", pokemon.abilities.toString())
                    intent.putExtra("peso", pokemon.weight) // Int
                    intent.putExtra("altura", pokemon.height) // Int
                    intent.putExtra("sprites", pokemon.sprites.toString()) // URLs en un string delimitadas por ', '
                    intent.putExtra("gritos", pokemon.cries.toString()) // Lo mismo acá
                    startActivity(intent)
                    finish()
                    boton.isEnabled = true
                }
            } catch (e: Exception) {
                // Manejo de errores
                withContext(Dispatchers.Main) {
                    boton.isEnabled = true
                    Log.e("API_ERROR", "Error en la llamada getPokemonPorId: ${e.message}")
                }
            }
        }
    }

    private fun cambiarSprite(spriteRequerida: Int, frente: ImageView, espalda: ImageView, spriteUrls: List<String>) {
        if(spriteUrls.isEmpty())
            return

        when(spriteRequerida){
            2 -> { //Femenino default
                Picasso.get().load(spriteUrls[1]).into(espalda)
                Picasso.get().load(spriteUrls[5]).into(frente)
            }
            3 -> { //Masculino shiny
                Picasso.get().load(spriteUrls[2]).into(espalda)
                Picasso.get().load(spriteUrls[6]).into(frente)
            }
            4 -> { //Femenino shiny
                Picasso.get().load(spriteUrls[3]).into(espalda)
                Picasso.get().load(spriteUrls[7]).into(frente)
            }
            else -> { //Masculino default
                Picasso.get().load(spriteUrls[0]).into(espalda)
                Picasso.get().load(spriteUrls[4]).into(frente)
            }
        }
    }

    private fun reproducirGrito(url: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mediaPlayer?.release()

            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener {
                    start()
                }
                setOnErrorListener { _, _, _ ->

                    false
                }
                prepareAsync()
            }
        }
    }
}

