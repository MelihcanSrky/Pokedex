package com.sarikaya.pokedex.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarikaya.pokedex.R
import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.common.util.returnColorResource
import com.sarikaya.pokedex.common.util.setImage
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.data.dto.detail.PokemonTypes
import com.sarikaya.pokedex.databinding.FragmentPokemonDetailBinding
import com.sarikaya.pokedex.presentation.detail.adapter.BaseStatsAdapter
import com.sarikaya.pokedex.presentation.detail.adapter.TypeChipAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val args: PokemonDetailFragmentArgs by navArgs()

    private val viewModel: PokemonDetailViewModel by viewModels()

    private val chipAdapter: TypeChipAdapter = TypeChipAdapter()

    private val baseStatsAdapter: BaseStatsAdapter = BaseStatsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPokemon(args.pokeName.lowercase())
        initBackButton()
        initPreviousButton()
        initNextButton()
        observeViewModel()
        initRecyclerView()
    }

    private fun initPreviousButton() {
        binding.arrowBeforeButton.setOnClickListener {
            viewModel.pokemonLiveData.value!!.data!!.id.let {
                println(it)
                if (it == 10001) {
                    viewModel.getPokemon("1017")
                } else {
                    viewModel.getPokemon((it - 1).toString())
                }
            }
        }
    }

    private fun initNextButton() {
        binding.arrowNextButton.setOnClickListener {
            viewModel.pokemonLiveData.value!!.data!!.id.let {
                println(it)
                if (it == 1017) {
                    viewModel.getPokemon("10001")
                } else {
                    viewModel.getPokemon((it + 1).toString())
                }
            }
        }
    }

    private fun initBackButton() {
        binding.arrowBackButton.setOnClickListener {
            findNavController()
                .popBackStack()
        }
    }

    private fun initRecyclerView() {
        val typeChipLayoutManager = LinearLayoutManager(this.requireContext())
        typeChipLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.chipRecyclerView.adapter = chipAdapter
        binding.chipRecyclerView.layoutManager = typeChipLayoutManager

        val baseStatsLayoutManager = LinearLayoutManager(this.requireContext())
        binding.baseStatsRecyclerView.adapter = baseStatsAdapter
        binding.baseStatsRecyclerView.layoutManager = baseStatsLayoutManager
    }

    private fun updateSuccessBaseStats(detail: PokemonDetailDto) {
        detail.id.let {
            if (it == 1)
                binding.arrowBeforeButton.visibility = View.GONE
            else {
                binding.arrowBeforeButton.visibility = View.VISIBLE
            }
        }
        binding.pokemonNameTitle.text = detail.name.replaceFirstChar {
            it.uppercase()
        }
        binding.pokemonNumberTitle.text = ("#${"%03d".format(detail.id)}")
        detail.sprites.other?.officialArtwork?.frontDefault?.let {
            binding.silhouetteImageView.setImage(it)
        }
        binding.weightDetailAboutCard.detailAboutCardTextView.text =
            "${((detail.weight).toFloat() / 10)} kg"
        binding.heightDetailAboutCard.apply {
            this.detailAboutCardTextView.text = "${((detail.height.toFloat()) / 10)} m"
            this.detailAboutCardImageView.setImageResource(R.drawable.straighten)
            this.detailAboutInfoText.text = resources.getString(R.string.height)
        }
        binding.movesDetailAboutCard.apply {
            detail.abilities?.let {
                if (it.size >= 2) {
                    this.detailAboutCardTextView.text =
                        "${it[0].ability.name}\n${it[1].ability.name}"
                } else {
                    this.detailAboutCardTextView.text = "${it[0].ability.name}"
                }
            }
        }
        detail.types.filter {
            it.slot == 1
        }[0].let {
            updateViewColors(it)
        }
        chipAdapter.setChips(detail.types)
        baseStatsAdapter.setStats(detail.stats)
    }

    private fun updateViewColors(type: PokemonTypes) {
        type.type.name.let { name ->
            binding.pokemonDetailFragment.setBackgroundResource(name.returnColorResource())
            val textColor = ContextCompat.getColor(
                requireContext(),
                name.returnColorResource()
            )
            binding.aboutTitleText.setTextColor(
                textColor
            )
            binding.baseStatsTitleText.setTextColor(
                textColor
            )
            baseStatsAdapter.setColor(name)
        }
    }

    private fun observeViewModel() {
        viewModel.pokemonLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        updateSuccessBaseStats(it)
                    }
                }

                is Resource.Error -> {
                    binding.pokemonStatsLinearLayout.visibility = View.INVISIBLE
                    binding.errorTextView.apply {
                        this.visibility = View.VISIBLE
                        this.text = response.message
                    }
                }
            }
        }
        viewModel.pokemonDescLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.pokemonDescriptionText.text = response.data
                }

                is Resource.Error -> {
                    binding.pokemonDescriptionText.text = response.message
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { response ->
            when (response) {
                true -> {
                    binding.pokemonStatsLinearLayout.visibility = View.INVISIBLE
                    binding.pokemonStatsProgressBar.visibility = View.VISIBLE
                    binding.arrowNextButton.visibility = View.GONE
                    binding.arrowBeforeButton.visibility = View.GONE
                }

                false -> {
                    binding.pokemonStatsLinearLayout.visibility = View.VISIBLE
                    binding.pokemonStatsProgressBar.visibility = View.INVISIBLE
                    binding.arrowNextButton.visibility = View.VISIBLE
                }
            }
        }
    }
}