package ru.eugeneproj.pokemon.presentation.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.eugeneproj.pokemon.R
import ru.eugeneproj.pokemon.data.model.Pokemon
import ru.eugeneproj.pokemon.databinding.ItemPokemonLayoutBinding

class PokemonViewHolder(private val binding: ItemPokemonLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, pokemonId: Int) {

        with(binding) {
            Glide.with(itemView)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png")
                .placeholder(R.drawable.no_pokemon_image_placeholder)
                .into(imageViewPokemonImage)
            textViewPokemonName.text = pokemon.name
        }
    }
}