package ru.eugeneproj.pokemon.data.network.repository

import retrofit2.Response
import ru.eugeneproj.pokemon.data.api.PokeApi
import ru.eugeneproj.pokemon.data.model.listitemmodel.PokemonResponse
import ru.eugeneproj.pokemon.data.model.pokemoninfomodel.PokemonInfo
import javax.inject.Inject

class PokemonsRepositoryIMPL @Inject constructor(private val api: PokeApi): PokemonsRepository {

    override suspend fun getPokemons(limit: Int, offset: Int): Response<PokemonResponse> =
        api.getPokemons(limit, offset)

    override suspend fun getPokemonInfo(pokemonName: String): Response<PokemonInfo>  =
        api.getPokemonInfo(pokemonName)
}