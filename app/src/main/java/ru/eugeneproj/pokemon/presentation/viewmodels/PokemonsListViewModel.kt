package ru.eugeneproj.pokemon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import ru.eugeneproj.pokemon.data.network.connection.ConnectivityRepository
import ru.eugeneproj.pokemon.data.network.repository.PokemonsRepository
import ru.eugeneproj.pokemon.data.network.repository.paging.PokemonsPagingSource
import ru.eugeneproj.pokemon.data.util.Constants
import javax.inject.Inject

@HiltViewModel
class PokemonsListViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository,
    private val connectivityRepository: ConnectivityRepository
) : ViewModel() {

    private val pokemonsListState = MutableStateFlow(Any())

    val isOnline = connectivityRepository.isConnected.asLiveData()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemons = pokemonsListState
        .flatMapLatest {
            Pager(
                config = Constants.PAGING_CONFIG,
                pagingSourceFactory = { PokemonsPagingSource(pokemonsRepository) }
            ).flow
        }.flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}