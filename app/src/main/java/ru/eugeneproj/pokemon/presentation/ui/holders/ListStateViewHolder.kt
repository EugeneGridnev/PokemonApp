package ru.eugeneproj.pokemon.presentation.ui.holders

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

abstract class ListStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    protected val context = view.context

    abstract fun bind(loadState: LoadState)
}