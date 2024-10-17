package com.example.grupoar_tp2024.recycleView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.activities.PokeDetallesActivity
import com.example.grupoar_tp2024.apiRest.PokemonDTO

class PokemonAdapter(var pokemones:MutableList<PokemonDTO>, var context:Context):
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view){
        var txtIDPoke: TextView
        var txtNombrePoke: TextView
        var txtTipoPoke: TextView

        init {
            txtIDPoke = view.findViewById(R.id.tv_idPoke)
            txtNombrePoke = view.findViewById(R.id.tv_nombrePoke)
            txtTipoPoke = view.findViewById(R.id.tv_tipoPoke)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.item_pokemones, parent, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount()= pokemones.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item= pokemones.get(position)
        holder.txtIDPoke.text= item.id.toString()
        holder.txtNombrePoke.text= item.name
        var textTipos = ""
        for(typeSlot in item.types)
            textTipos += "${typeSlot.type.name} "
        holder.txtTipoPoke.text= textTipos

        holder.itemView.setOnClickListener{
            val intent = Intent(context, PokeDetallesActivity::class.java)

            intent.putExtra("id", item.id) //Int
            intent.putExtra("nombre", item.name)
            intent.putExtra("tipo", item.types.toString())
            intent.putExtra("movimientos", item.moves.toString())
            intent.putExtra("habilidades", item.abilities.toString())
            intent.putExtra("peso", item.weight) //Int
            intent.putExtra("altura", item.height) //Int
            intent.putExtra("sprites", item.sprites.toString()) //Son urls en un string delimitadas por ', '
            intent.putExtra("gritos", item.cries.toString()) //Lo mismo aca

            context.startActivity(intent)

        }

    }

    fun updateData(newPokemons: MutableList<PokemonDTO>) {
        pokemones = newPokemons
        notifyDataSetChanged()  // Notifica que los datos han cambiado
    }

}


