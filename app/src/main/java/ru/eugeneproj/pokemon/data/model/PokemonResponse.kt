package ru.eugeneproj.pokemon.data.model

data class PokemonResponse(
    val count: Int,
    val next: Int?,
    val previous: Int?,
    val pokemons: List<Pokemon>
)