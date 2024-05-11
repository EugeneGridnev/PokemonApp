package ru.eugeneproj.pokemon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.eugeneproj.pokemon.presentation.ui.holders.ErrorListStateViewHolder
import ru.eugeneproj.pokemon.presentation.ui.holders.ListStateViewHolder
import ru.eugeneproj.pokemon.presentation.ui.holders.LoadingStateViewHolder

class PokemonsLoadingStateAdapter : LoadStateAdapter<ListStateViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {

        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ListStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ListStateViewHolder {
        return when (loadState) {
            LoadState.Loading -> LoadingStateViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorListStateViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {

        private const val ERROR = 1
        private const val PROGRESS = 0
    }

}