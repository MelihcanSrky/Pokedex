package com.sarikaya.pokedex.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarikaya.pokedex.common.Constants
import com.sarikaya.pokedex.common.Constants.PAGE_LIMIT
import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.domain.model.PokemonList
import com.sarikaya.pokedex.domain.usecase.UseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseWrapper: UseCaseWrapper
) : ViewModel() {

    private var endReached = false

    private val _pokemonListLiveData: MutableLiveData<Resource<PokemonList>> = MutableLiveData()
    val pokemonListLiveData = _pokemonListLiveData

    private val _pokemonSearchLiveData: MutableLiveData<Resource<PokemonList>> = MutableLiveData()
    val pokemonSearchLiveData = _pokemonSearchLiveData

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading = _isLoading

    private val _searchBy: MutableLiveData<Constants.SearchBy> =
        MutableLiveData(Constants.SearchBy.NUMBER)
    val searchBy = _searchBy

    fun getPokemonList(curPage: Int) {
        if (!endReached && !isLoading.value!!) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                useCaseWrapper.getPokemonListUseCase.invoke(curPage * PAGE_LIMIT)
                    .let {
                        endReached = it.data?.next == null
                        _pokemonListLiveData.postValue(it)
                    }
                _isLoading.postValue(false)
            }
        }
    }

    fun searchPokemon(pokeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            useCaseWrapper.searchPokemonUseCase.invoke(pokeId)
                .let {
                    _pokemonSearchLiveData.postValue(it)
                }
            _isLoading.postValue(false)
        }
    }

    fun changeSearchBy() {
        if (_searchBy.value == Constants.SearchBy.NUMBER)
            _searchBy.postValue(Constants.SearchBy.NAME)
        else
            _searchBy.postValue(Constants.SearchBy.NUMBER)

    }
}