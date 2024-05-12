package ru.eugeneproj.pokemon.data.model.pokemoninfomodel

data class PokemonInfo(
    val height: Int,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)