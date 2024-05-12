package ru.eugeneproj.pokemon.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.eugeneproj.pokemon.data.network.repository.PokemonsRepository
import ru.eugeneproj.pokemon.presentation.state.DescriptionFragmentState
import javax.inject.Inject

@HiltViewModel
class PokemonDescriptionViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository
) : ViewModel() {

    private val _descriptionState: MutableStateFlow<DescriptionFragmentState> =
        MutableStateFlow(DescriptionFragmentState.Loading)
    val description: StateFlow<DescriptionFragmentState> = _descriptionState

    fun getPokemonInfo(pokemonName: String) {
        _descriptionState.value = DescriptionFragmentState.Loading
        viewModelScope.launch {
            fetchPokemon(pokemonName)
        }
    }

    private suspend fun fetchPokemon(pokemonName: String) {

        _descriptionState.value = try {

            val response = pokemonsRepository.getPokemonInfo(pokemonName)

            if (response.isSuccessful) {
                val pokemon = checkNotNull(response.body())
                DescriptionFragmentState.Pokemon(pokemon)
            } else {
                DescriptionFragmentState.Error(HttpException(response))
            }
        } catch (e: Exception) {
            DescriptionFragmentState.Error(e)
        }
    }
}