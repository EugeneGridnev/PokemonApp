package ru.eugeneproj.pokemon.presentation.state

import ru.eugeneproj.pokemon.data.model.pokemoninfomodel.PokemonInfo

sealed class DescriptionFragmentState() {
    data object Loading : DescriptionFragmentState()

    data class Pokemon(val data: PokemonInfo) : DescriptionFragmentState()

    data class Error(
        val error: Exception
    ) : DescriptionFragmentState()
}