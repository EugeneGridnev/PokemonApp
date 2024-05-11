package ru.eugeneproj.pokemon.data.network.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.eugeneproj.pokemon.data.model.Pokemon
import ru.eugeneproj.pokemon.data.network.repository.PokemonsRepository
import ru.eugeneproj.pokemon.data.util.Constants

class PokemonsPagingSource(
    val pokemonsRepository: PokemonsRepository
) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {

        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize: Int = params.loadSize.coerceAtMost(Constants.PAGE_SIZE)
        val skip = pageNumber * pageSize

        try {
            val response = pokemonsRepository.getPokemons(pageSize, skip)

            if (response.isSuccessful) {
                val pokemons = checkNotNull(response.body()).results.map { pokemon ->
                    Pokemon(
                        name = pokemon.name,
                        url = pokemon.url
                    )
                }

                val nextPageNumber =
                    if (skip + pokemons.size >= (response.body()?.count ?: pageSize)) null
                    else pageNumber + 1
                val prevPageNumber = if (pageNumber == 0) null else pageNumber - 1

                return LoadResult.Page(pokemons, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 0
    }
}