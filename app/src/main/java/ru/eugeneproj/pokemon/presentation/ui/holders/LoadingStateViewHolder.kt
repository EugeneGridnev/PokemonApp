package ru.eugeneproj.pokemon.presentation.ui.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import ru.eugeneproj.pokemon.databinding.ItemProgressBinding

class LoadingStateViewHolder(
    private val binding: ItemProgressBinding
) : ListStateViewHolder(binding.root) {

    constructor(
        layoutInflater: LayoutInflater,
        parent: ViewGroup? = null,
        attachToRoot: Boolean = false
    ) : this(
        ItemProgressBinding.inflate(
            layoutInflater,
            parent,
            attachToRoot
        )
    )

    override fun bind(loadState: LoadState) {}
}