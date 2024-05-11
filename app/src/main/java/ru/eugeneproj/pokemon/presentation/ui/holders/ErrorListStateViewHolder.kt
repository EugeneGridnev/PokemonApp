package ru.eugeneproj.pokemon.presentation.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import ru.eugeneproj.pokemon.R
import ru.eugeneproj.pokemon.databinding.ItemErrorBinding


class ErrorListStateViewHolder(
    private val binding: ItemErrorBinding
) : ListStateViewHolder(binding.root) {

    constructor(
        layoutInflater: LayoutInflater,
        parent: ViewGroup? = null,
        attachToRoot: Boolean = false
    ) : this(
        ItemErrorBinding.inflate(
            layoutInflater,
            parent,
            attachToRoot
        )
    )

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.errorMessage.text = context.getText(R.string.list_load_error)
    }

}