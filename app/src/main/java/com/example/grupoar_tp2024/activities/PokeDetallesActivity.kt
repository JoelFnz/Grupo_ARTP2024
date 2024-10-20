package com.example.grupoar_tp2024.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
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
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback as Cb
import retrofit2.Response


class PokeDetallesActivity : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)

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
        val btnSiguiente: Button = findViewById(R.id.siguiente)
        val btnAnterior: Button = findViewById(R.id.anterior)
        val btnSpriteFem: Button = findViewById(R.id.spriteFem)
        val btnSpriteShiny: Button = findViewById(R.id.spriteShiny)
        val txtPeso: TextView = findViewById(R.id.peso)
        val txtAltura: TextView = findViewById(R.id.altura)

        txtNombre.text = intent.getStringExtra("nombre")
        etId.text = "ID: ${intent.getIntExtra("id", -1)}"
        txtTipo.text = intent.getStringExtra("tipo")
        txtMovimiento.text = intent.getStringExtra("movimientos")
        txtHabilidades.text = intent.getStringExtra("habilidades")
        txtPeso.text = txtPeso.text.toString() + (intent.getIntExtra("peso", -1).toFloat() / 10) + " kg"
        txtAltura.text = txtAltura.text.toString() + (intent.getIntExtra("altura", -1).toFloat() / 10) + " m"
        //txtRegion.text = region

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

        btnSiguiente.setOnClickListener{
            btnSiguiente.isEnabled = false
            getPokemon(intent.getIntExtra("id", -1) + 1, btnSiguiente)
        }

        btnAnterior.setOnClickListener{
            btnAnterior.isEnabled = false
            getPokemon(intent.getIntExtra("id", -1) - 1, btnAnterior)
        }


    }

    private fun getPokemon(id: Int, boton: Button){

        var idActual = id
        if(idActual < 1 || idActual > 10277){
            boton.isEnabled = true
            return
        }


        if(idActual == 1026 )
            idActual = 10001 //El ultimo pokemon de la pokedex tiene id 1025,
                            //pero la api usa como id > 10000 para pokemones especiales

        val intent = Intent(this, PokeDetallesActivity::class.java)

        api.getPokemonPorId(idActual.toLong()).enqueue(object: Cb<PokemonDTO>{
            override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                if(response.isSuccessful) {
                    val pokemon = response.body()
                    intent.putExtra("id", pokemon!!.id) //Int
                    intent.putExtra("nombre", pokemon.name)
                    intent.putExtra("tipo", pokemon.types.toString())
                    intent.putExtra("movimientos", pokemon.moves.toString())
                    intent.putExtra("habilidades", pokemon.abilities.toString())
                    intent.putExtra("peso", pokemon.weight) //Int
                    intent.putExtra("altura", pokemon.height) //Int
                    intent.putExtra(
                        "sprites",
                        pokemon.sprites.toString()
                    ) //Son urls en un string delimitadas por ', '
                    intent.putExtra("gritos", pokemon.cries.toString()) //Lo mismo aca
                    startActivity(intent)
                    finish()
                }
                else {
                    Log.e(
                        "API_ERROR",
                        "Error en la llamada getPokemonPorUrl: ${response.message()}"
                    )
                }
                boton.isEnabled = true
            }

            override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                boton.isEnabled = true
                Log.e("API_ERROR", "Error en la llamada getPokemonPorUrl: ${t.message}")
            }

        })

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
}