package com.example.grupoar_tp2024

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

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

        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)

        rvPokemones=findViewById(R.id.rvListaPokemones)
        pokemonAdapter = PokemonAdapter(getPokemones(), this)

        rvPokemones.adapter = pokemonAdapter
    }

    private fun getPokemones(): MutableList<Pokemones> {
        var pokemones: MutableList<Pokemones> = ArrayList()
        pokemones.add(Pokemones(1, "Bulbasaur", listOf("Planta", "Veneno"), listOf("Espesura"), "Kanto"))
        pokemones.add(Pokemones(2, "Ivysaur", listOf("Planta", "Veneno"), listOf("Espesura"), "Kanto"))
        pokemones.add(Pokemones(3, "Venusaur", listOf("Planta", "Veneno"), listOf("Espesura"), "Kanto"))
        pokemones.add(Pokemones(4, "Charmander", listOf("Fuego"), listOf("Mar Llamas"), "Kanto"))
        pokemones.add(Pokemones(5, "Charmeleon", listOf("Fuego"), listOf("Mar Llamas"), "Kanto"))
        pokemones.add(Pokemones(6, "Charizard", listOf("Fuego", "Volador"), listOf("Mar Llamas"), "Kanto"))
        pokemones.add(Pokemones(7, "Squirtle", listOf("Agua"), listOf("Torrente"), "Kanto"))
        pokemones.add(Pokemones(8, "Wartortle", listOf("Agua"), listOf("Torrente"), "Kanto"))
        pokemones.add(Pokemones(9, "Blastoise", listOf("Agua"), listOf("Torrente"), "Kanto"))
        pokemones.add(Pokemones(10, "Caterpie", listOf("Bicho"), listOf("Polvo Escudo"), "Kanto"))
        pokemones.add(Pokemones(11, "Metapod", listOf("Bicho"), listOf("Mudar"), "Kanto"))
        pokemones.add(Pokemones(12, "Butterfree", listOf("Bicho", "Volador"), listOf("Ojo Compuesto"), "Kanto"))
        pokemones.add(Pokemones(13, "Weedle", listOf("Bicho", "Veneno"), listOf("Polvo Escudo"), "Kanto"))
        pokemones.add(Pokemones(14, "Kakuna", listOf("Bicho", "Veneno"), listOf("Mudar"), "Kanto"))
        pokemones.add(Pokemones(15, "Beedrill", listOf("Bicho", "Veneno"), listOf("Enjambre"), "Kanto"))
        pokemones.add(Pokemones(16, "Pidgey", listOf("Normal", "Volador"), listOf("Vista Lince", "Tumbos"), "Kanto"))
        pokemones.add(Pokemones(17, "Pidgeotto", listOf("Normal", "Volador"), listOf("Vista Lince", "Tumbos"), "Kanto"))
        pokemones.add(Pokemones(18, "Pidgeot", listOf("Normal", "Volador"), listOf("Vista Lince", "Tumbos"), "Kanto"))
        pokemones.add(Pokemones(19, "Rattata", listOf("Normal"), listOf("Fuga", "Agallas"), "Kanto"))
        pokemones.add(Pokemones(20, "Raticate", listOf("Normal"), listOf("Fuga", "Agallas"), "Kanto"))
        pokemones.add(Pokemones(21, "Spearow", listOf("Normal", "Volador"), listOf("Vista Lince"), "Kanto"))
        pokemones.add(Pokemones(22, "Fearow", listOf("Normal", "Volador"), listOf("Vista Lince"), "Kanto"))
        pokemones.add(Pokemones(23, "Ekans", listOf("Veneno"), listOf("Muda", "Intimidación"), "Kanto"))
        pokemones.add(Pokemones(24, "Arbok", listOf("Veneno"), listOf("Muda", "Intimidación"), "Kanto"))
        pokemones.add(Pokemones(25, "Pikachu", listOf("Eléctrico"), listOf("Electricidad estática"), "Kanto"))
        pokemones.add(Pokemones(26, "Raichu", listOf("Eléctrico"), listOf("Electricidad estática"), "Kanto"))
        pokemones.add(Pokemones(27, "Sandshrew", listOf("Tierra"), listOf("Velo Arena"), "Kanto"))
        pokemones.add(Pokemones(28, "Sandslash", listOf("Tierra"), listOf("Velo Arena"), "Kanto"))
        pokemones.add(Pokemones(29, "Nidoran♀", listOf("Veneno"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(30, "Nidorina", listOf("Veneno"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(31, "Nidoqueen", listOf("Veneno", "Tierra"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(32, "Nidoran♂", listOf("Veneno"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(33, "Nidorino", listOf("Veneno"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(34, "Nidoking", listOf("Veneno", "Tierra"), listOf("Punto Tóxico", "Rivalidad"), "Kanto"))
        pokemones.add(Pokemones(35, "Clefairy", listOf("Hada"), listOf("Gran Encanto", "Muro Mágico"), "Kanto"))
        pokemones.add(Pokemones(36, "Clefable", listOf("Hada"), listOf("Gran Encanto", "Muro Mágico"), "Kanto"))
        pokemones.add(Pokemones(37, "Vulpix", listOf("Fuego"), listOf("Absorbe Fuego"), "Kanto"))
        pokemones.add(Pokemones(38, "Ninetales", listOf("Fuego"), listOf("Absorbe Fuego"), "Kanto"))
        pokemones.add(Pokemones(39, "Jigglypuff", listOf("Normal", "Hada"), listOf("Gran Encanto", "Tenacidad"), "Kanto"))
        pokemones.add(Pokemones(40, "Wigglytuff", listOf("Normal", "Hada"), listOf("Gran Encanto", "Tenacidad"), "Kanto"))
        pokemones.add(Pokemones(41, "Zubat", listOf("Veneno", "Volador"), listOf("Fuerza Mental"), "Kanto"))
        pokemones.add(Pokemones(42, "Golbat", listOf("Veneno", "Volador"), listOf("Fuerza Mental"), "Kanto"))
        pokemones.add(Pokemones(43, "Diglett", listOf("Tierra"), listOf("Velo Arena", "Trampa Arena"), "Kanto"))
        pokemones.add(Pokemones(44, "Dugtrio", listOf("Tierra"), listOf("Velo Arena", "Trampa Arena"), "Kanto"))
        pokemones.add(Pokemones(45, "Meowth", listOf("Normal"), listOf("Recogida", "Experto"), "Kanto"))
        pokemones.add(Pokemones(46, "Persian", listOf("Normal"), listOf("Experto", "Flixibilidad"), "Kanto"))
        pokemones.add(Pokemones(47, "Psyduck", listOf("Agua"), listOf("Humedad", "Aclimatación"), "Kanto"))
        pokemones.add(Pokemones(48, "Golduck", listOf("Agua"), listOf("Humedad", "Aclimatación"), "Kanto"))
        pokemones.add(Pokemones(49, "Mankey", listOf("Lucha"), listOf("Espíritu Vital", "Irascible"), "Kanto"))
        pokemones.add(Pokemones(50, "Primeape", listOf("Lucha"), listOf("Espíritu Vital", "Irascible"), "Kanto"))
        return pokemones
    }
}