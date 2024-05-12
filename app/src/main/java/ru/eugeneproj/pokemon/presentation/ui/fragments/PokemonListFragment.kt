package ru.eugeneproj.pokemon.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.eugeneproj.pokemon.R
import ru.eugeneproj.pokemon.databinding.FragmentPokemonListBinding
import ru.eugeneproj.pokemon.presentation.ui.adapters.PokemonsLoadingStateAdapter
import ru.eugeneproj.pokemon.presentation.ui.adapters.PokemonsPagingAdapter
import ru.eugeneproj.pokemon.presentation.viewmodels.PokemonsListViewModel

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding: FragmentPokemonListBinding
        get() = _binding!!

    private val viewModel: PokemonsListViewModel by viewModels()
    private lateinit var pokemonsAdapter: PokemonsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPokemonsList()
        observePokemons()
        observeNetworkState()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpPokemonsList() {

        pokemonsAdapter = PokemonsPagingAdapter(
            onItemClickListener = { onPokemonClick(it.name) }
        )

        with(binding) {
            recyclerViewPokemons.layoutManager = LinearLayoutManager(context)
            recyclerViewPokemons.adapter =
                pokemonsAdapter.withLoadStateFooter(PokemonsLoadingStateAdapter())

            initSwipeToRefresh(pokemonsAdapter)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    pokemonsAdapter.loadStateFlow.collect { loadStates ->
                        swipeRefresh.isRefreshing =
                            loadStates.mediator?.refresh is LoadState.Loading
                    }
                }
            }
        }

        setListStateListener(pokemonsAdapter)

    }

    private fun observePokemons() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemons.collectLatest(pokemonsAdapter::submitData)
            }
        }
    }

    private fun onPokemonClick(pokemonName: String) {

        val action =
            PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDescriptionFragment(
                pokemonName
            )
        findNavController().navigate(action)

    }

    private fun initSwipeToRefresh(adapter: PokemonsPagingAdapter) {

        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun setListStateListener(adapter: PokemonsPagingAdapter) {

        adapter.addLoadStateListener { combinedLoadStates ->

            with(binding) {
                when (combinedLoadStates.refresh) {
                    is LoadState.Error -> {
                        recyclerViewPokemons.isVisible = true
                        progressBar.isVisible = false

                        Toast.makeText(
                            context,
                            resources.getString(R.string.toast_load_error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    LoadState.Loading -> {
                        recyclerViewPokemons.isVisible = false
                        progressBar.isVisible = true
                    }

                    is LoadState.NotLoading -> {
                        if (combinedLoadStates.append.endOfPaginationReached && pokemonsAdapter.itemCount == 0) {
                            recyclerViewPokemons.isVisible = false
                            progressBar.isVisible = false
                        } else {
                            recyclerViewPokemons.isVisible = true
                            progressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun observeNetworkState() {

        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            when (isOnline) {
                true -> pokemonsAdapter.retry()
                false -> Toast.makeText(
                    context,
                    resources.getString(R.string.network_error_message),
                    Toast.LENGTH_SHORT
                ).show()

                null -> {}
            }
        }
    }
}