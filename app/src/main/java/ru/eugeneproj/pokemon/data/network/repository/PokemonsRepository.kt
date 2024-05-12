package ru.eugeneproj.pokemon.data.network.repository

import retrofit2.Response
import ru.eugeneproj.pokemon.data.model.listitemmodel.PokemonResponse
import ru.eugeneproj.pokemon.data.model.pokemoninfomodel.PokemonInfo

interface PokemonsRepository {

    suspend fun getPokemons(limit: Int, offset: Int): Response<PokemonResponse>

    suspend fun getPokemonInfo(pokemonName: String): Response<PokemonInfo>
}