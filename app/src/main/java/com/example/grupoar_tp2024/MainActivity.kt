package com.example.grupoar_tp2024

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rvPokemones: RecyclerView
    lateinit var pokemonAdapter: PokemonAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPokemones=findViewById(R.id.rvListaPokemones)
        pokemonAdapter = PokemonAdapter(getPokemones(), this)

        rvPokemones.adapter = pokemonAdapter
    }

    private fun getPokemones(): MutableList<Pokemones> {
        var pokemones: MutableList<Pokemones> = ArrayList()
        pokemones.add(Pokemones(1, "Bulbasaur", "Planta"))
        pokemones.add(Pokemones(2, "Ivysaur", "Planta"))
        pokemones.add(Pokemones(3, "Venusaur", "Planta"))
        pokemones.add(Pokemones(4, "Charmander", "Fuego"))
        pokemones.add(Pokemones(5, "Charmeleon", "Fuego"))
        pokemones.add(Pokemones(6, "Charizard", "Fuego"))
        pokemones.add(Pokemones(7, "Squirtle", "Agua"))
        pokemones.add(Pokemones(8, "Wartortle", "Agua"))
        pokemones.add(Pokemones(9, "Blastoise", "Agua"))
        pokemones.add(Pokemones(10, "Caterpie", "Bicho"))
        pokemones.add(Pokemones(11, "Metapod", "Bicho"))
        pokemones.add(Pokemones(12, "Butterfree", "Bicho"))
        pokemones.add(Pokemones(13, "Weedle", "Bicho"))
        pokemones.add(Pokemones(14, "Kakuna", "Bicho"))
        pokemones.add(Pokemones(15, "Beedrill", "Bicho"))
        pokemones.add(Pokemones(16, "Pidgey", "Normal"))
        pokemones.add(Pokemones(17, "Pidgeotto", "Normal"))
        pokemones.add(Pokemones(18, "Pidgeot", "Normal"))
        pokemones.add(Pokemones(19, "Rattata", "Normal"))
        pokemones.add(Pokemones(20, "Raticate", "Normal"))
        pokemones.add(Pokemones(21, "Spiro", "Normal"))
        pokemones.add(Pokemones(22, "Fearow", "Normal"))
        pokemones.add(Pokemones(23, "Ekans", "Veneno"))
        pokemones.add(Pokemones(24, "Arbok", "Veneno"))
        pokemones.add(Pokemones(25, "Pikachu", "Eléctrico"))
        pokemones.add(Pokemones(26, "Raichu", "Eléctrico"))
        pokemones.add(Pokemones(27, "Sandshrew", "Tierra"))
        pokemones.add(Pokemones(28, "Sandslash", "Tierra"))
        pokemones.add(Pokemones(29, "Nidoran♀", "Veneno"))
        pokemones.add(Pokemones(30, "Nidorina", "Veneno"))
        pokemones.add(Pokemones(31, "Nidoqueen", "Veneno"))
        pokemones.add(Pokemones(32, "Nidoran♂", "Veneno"))
        pokemones.add(Pokemones(33, "Nidorino", "Veneno"))
        pokemones.add(Pokemones(34, "Nidoking", "Veneno"))
        pokemones.add(Pokemones(35, "Clefairy", "Hada"))
        pokemones.add(Pokemones(36, "Clefable", "Hada"))
        pokemones.add(Pokemones(37, "Vulpix", "Fuego"))
        pokemones.add(Pokemones(38, "Ninetales", "Fuego"))
        pokemones.add(Pokemones(39, "Jigglypuff", "Normal"))
        pokemones.add(Pokemones(40, "Wigglytuff", "Normal"))
        pokemones.add(Pokemones(41, "Zubat", "Veneno"))
        pokemones.add(Pokemones(42, "Golbat", "Veneno"))
        pokemones.add(Pokemones(43, "Diglett", "Tierra"))
        pokemones.add(Pokemones(44, "Dugtrio", "Tierra"))
        pokemones.add(Pokemones(45, "Meowth", "Normal"))
        pokemones.add(Pokemones(46, "Persian", "Normal"))
        pokemones.add(Pokemones(47, "Psyduck", "Agua"))
        pokemones.add(Pokemones(48, "Golduck", "Agua"))
        pokemones.add(Pokemones(49, "Mankey", "Lucha"))
        pokemones.add(Pokemones(50, "Primeape", "Lucha"))
        return pokemones
    }
}