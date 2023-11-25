package com.sarikaya.pokedex.presentation.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarikaya.pokedex.R
import com.sarikaya.pokedex.common.Constants
import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.common.util.convertToPokeId
import com.sarikaya.pokedex.common.util.filterDigits
import com.sarikaya.pokedex.databinding.FragmentHomeBinding
import com.sarikaya.pokedex.presentation.home.adapter.PokedexAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = PokedexAdapter()

    private var closeDrawable: Drawable? = null

    private var curPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.pokemonListLiveData.value == null) {
            viewModel.getPokemonList(0)
        }

        initRecyclerView()
        initEditTextListener()
        initSearchByButton()
        observeViewModel()
    }

    private fun initRecyclerView() {
        var totalItemCount: Int
        var lastVisibleItem: Int
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.pokedexRecyclerView.requestFocus()
        binding.pokedexRecyclerView.adapter = adapter
        binding.pokedexRecyclerView.layoutManager = layoutManager
        binding.pokedexRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.searchEditText.clearFocus()

                if (dy > 0) {
                    totalItemCount = layoutManager.getItemCount()
                    lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItem == totalItemCount - 1) {
                        curPage++
                        viewModel.getPokemonList(curPage)
                    }
                }
            }
        })
    }

    private fun initEditTextListener() {
        closeDrawable = binding.searchEditText.compoundDrawablesRelative[2]
        closeDrawable?.let {
            it.mutate().alpha = 0
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSeq: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSeq.toString().convertToPokeId().isNotEmpty()) {
                    if (viewModel.searchBy.value == Constants.SearchBy.NAME) {
                        var pokeName = charSeq.toString()
                        pokeName = pokeName.filterDigits()
                        if (pokeName.isNotEmpty()) {
                            viewModel.searchPokemon(pokeName)
                        }
                    } else {
                        viewModel.searchPokemon(charSeq.toString().convertToPokeId())
                    }
                } else if (charSeq.isNullOrEmpty() || charSeq.isBlank()) {
                    adapter.changeShowList(false)
                } else {
                    adapter.changeShowList(false)
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                if (viewModel.searchBy.value == Constants.SearchBy.NUMBER) {
                    if (!editable.toString().startsWith("#") && !editable.isNullOrEmpty()) {
                        binding.searchEditText.setText("#${editable}")
                        binding.searchEditText.setSelection(2)
                    }
                }
            }
        })
        initEditTextFocusChange()
        drawableOnTouchListener()
    }

    private fun initEditTextFocusChange() {
        binding.searchEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus || binding.searchEditText.text.isNotEmpty()) {
                view.elevation = resources.getDimension(R.dimen.edit_text_focused_elevation)
                closeDrawable?.mutate()?.alpha = 255
            } else {
                view.elevation = resources.getDimension(R.dimen.edit_text_default_elevation)
                closeDrawable?.mutate()?.alpha = 0
            }
        }
    }

    private fun drawableOnTouchListener() {
        binding.searchEditText.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.searchEditText.compoundDrawablesRelative[2]
                if (drawableEnd != null && motionEvent.rawX >= binding.searchEditText.right - drawableEnd.bounds.width()) {
                    binding.searchEditText.clearFocus()
                    binding.pokedexRecyclerView.requestFocus()
                    binding.searchEditText.text.clear()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun initSearchByButton() {
        binding.searchByButton.setOnClickListener {
            viewModel.changeSearchBy()
        }
    }

    private fun observeViewModel() {
        viewModel.pokemonListLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.results?.let {
                        adapter.setList(it)
                        adapter.setOnItemClickListener { name ->
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToPokemonDetailFragment(
                                    name
                                )
                            findNavController().navigate(action)
                        }
                    }
                }

                is Resource.Error -> {
                    Log.e("x", response.message.toString())
                }
            }
        }

        viewModel.pokemonSearchLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    adapter.changeShowList(true)
                    response.data?.let { adapter.setSearchResults(it.results) }
                }

                is Resource.Error -> {
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loadingBar.visibility = View.VISIBLE
            } else {
                binding.loadingBar.visibility = View.GONE
            }
        }

        viewModel.searchBy.observe(viewLifecycleOwner) { searchBy ->
            when (searchBy) {
                Constants.SearchBy.NAME -> {
                    binding.searchEditText.inputType = InputType.TYPE_CLASS_TEXT
                    binding.searchByButton.setImageResource(R.drawable.text_format)
                }

                Constants.SearchBy.NUMBER -> {
                    binding.searchEditText.inputType = InputType.TYPE_CLASS_NUMBER
                    binding.searchByButton.setImageResource(R.drawable.tag)
                }

                else -> {}
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}