package ru.eugeneproj.pokemon.data.model.pokemoninfomodel

data class PokemonInfo(
    val height: Float,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Float
)