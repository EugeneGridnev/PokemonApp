package ru.eugeneproj.pokemon.presentation.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import ru.eugeneproj.pokemon.databinding.ItemProgressBinding

class LoadingStateViewHolder(
    private val binding: ItemProgressBinding
) : ListStateViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {}
}