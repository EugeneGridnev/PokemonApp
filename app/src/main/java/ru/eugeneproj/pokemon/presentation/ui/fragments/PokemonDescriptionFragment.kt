package ru.eugeneproj.pokemon.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.eugeneproj.pokemon.R
import ru.eugeneproj.pokemon.data.model.pokemoninfomodel.PokemonInfo
import ru.eugeneproj.pokemon.databinding.FragmentPokemonDescriptionBinding
import ru.eugeneproj.pokemon.presentation.state.DescriptionFragmentState
import ru.eugeneproj.pokemon.presentation.viewmodels.PokemonDescriptionViewModel

@AndroidEntryPoint
class PokemonDescriptionFragment : Fragment() {

    private var _binding: FragmentPokemonDescriptionBinding? = null
    private val binding: FragmentPokemonDescriptionBinding
        get() = _binding!!

    private val args: PokemonDescriptionFragmentArgs by navArgs()
    private val viewModel: PokemonDescriptionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDescriptionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPokemonInfo(args.pokemonName)
        setUpUi()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpUi() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.description.collect { loadStates ->
                    when (loadStates) {
                        DescriptionFragmentState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.wholePokemonDescription.isVisible = false
                        }

                        is DescriptionFragmentState.Pokemon -> {
                            binding.progressBar.isVisible = false
                            binding.wholePokemonDescription.isVisible = true
                            setUpData(loadStates.data)
                        }

                        is DescriptionFragmentState.Error -> {
                            val action =
                                PokemonDescriptionFragmentDirections.actionPokemonDescriptionFragmentToErrorFragment(
                                    args.pokemonName
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpData(pokemonInfo: PokemonInfo) {

        val weightKG = pokemonInfo.weight / 10
        val heightM = pokemonInfo.height / 10

        with(binding) {
            imageViewPokemonImage.let {
                Glide.with(this@PokemonDescriptionFragment)
                    .load(pokemonInfo.sprites.frontDefault)
                    .placeholder(R.drawable.no_pokemon_image_placeholder)
                    .into(it)
            }
            textViewPokemonName.text = pokemonInfo.name
            textViewPokemonWeight.text = "$weightKG kg"
            textViewPokemonHeight.text = "$heightM m"
            textViewPokemonType.text = pokemonInfo.types.joinToString(", ") { it.type.name }
            pokemonInfo.stats.forEach {
                when(it.stat.name) {
                    "hp" -> textViewPokemonHP.text = it.statValue.toString()
                    "attack" -> textViewPokemonAttack.text = it.statValue.toString()
                    "defense" -> textViewPokemonDefense.text = it.statValue.toString()
                    "special-attack" -> textViewPokemonSpecialAttack.text = it.statValue.toString()
                    "special-defense" -> textViewPokemonSpecialDefense.text = it.statValue.toString()
                    "speed" -> textViewPokemonSpeed.text = it.statValue.toString()
                }
            }
        }
    }
}