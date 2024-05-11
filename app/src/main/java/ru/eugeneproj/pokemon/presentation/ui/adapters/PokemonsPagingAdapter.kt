package ru.eugeneproj.pokemon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.eugeneproj.pokemon.data.model.Pokemon
import ru.eugeneproj.pokemon.databinding.ItemPokemonLayoutBinding
import ru.eugeneproj.pokemon.presentation.ui.holders.PokemonViewHolder

class PokemonsPagingAdapter(private val onItemClickListener: ((Pokemon) -> Unit)?) :
    PagingDataAdapter<Pokemon, PokemonViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        return PokemonViewHolder(
            ItemPokemonLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        val pokemon = getItem(position) ?: return
        val pokemonId = pokemon.url.toUri().lastPathSegment?.toInt()
        holder.bind(pokemon, pokemonId!!)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(pokemon)
        }
    }


    class MovieDiffCallBack : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
}