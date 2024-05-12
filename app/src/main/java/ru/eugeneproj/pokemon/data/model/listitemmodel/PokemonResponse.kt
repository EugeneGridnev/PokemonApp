package ru.eugeneproj.pokemon.data.model.listitemmodel

data class PokemonResponse(
    val count: Int,
    val next: String? = "",
    val previous: String? = "",
    val results: List<Pokemon>
)