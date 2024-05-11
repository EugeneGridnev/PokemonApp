package ru.eugeneproj.pokemon.presentation.ui.holders

import androidx.paging.LoadState
import ru.eugeneproj.pokemon.R
import ru.eugeneproj.pokemon.databinding.ItemErrorBinding

class ErrorListStateViewHolder(
    private val binding: ItemErrorBinding
) : ListStateViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.errorMessage.text = context.getText(R.string.list_load_error)
    }

}