package ru.eugeneproj.pokemon.data.api

import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query
import ru.eugeneproj.pokemon.data.model.PokemonResponse

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit")
        limit: Int,
        @Query("offset")
        offset: Int
    ): Response<PokemonResponse>
}