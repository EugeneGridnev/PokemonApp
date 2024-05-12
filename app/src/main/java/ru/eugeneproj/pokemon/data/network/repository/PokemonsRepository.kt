package ru.eugeneproj.pokemon.data.network.repository

import retrofit2.Response
import ru.eugeneproj.pokemon.data.model.listitemmodel.PokemonResponse

interface PokemonsRepository {

    suspend fun getPokemons(limit: Int, offset: Int): Response<PokemonResponse>
}