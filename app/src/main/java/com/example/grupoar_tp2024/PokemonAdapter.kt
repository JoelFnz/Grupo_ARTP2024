package com.example.grupoar_tp2024

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter(var pokemones:MutableList<Pokemones>, var context:Context):
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
        holder.txtNombrePoke.text= item.nombre
        holder.txtTipoPoke.text= item.tipo.joinToString(" - ")

        holder.itemView.setOnClickListener{
            val intent = Intent(context, PokeDetallesActivity::class.java)

            intent.putExtra("id", item.id)
            intent.putExtra("nombre", item.nombre)
            intent.putExtra("tipo", item.tipo.joinToString(" - "))
            intent.putExtra("movimientos", item.movimientos.joinToString(" - "))
            intent.putExtra("region", item.region)

            context.startActivity(intent)

        }

    }

}


