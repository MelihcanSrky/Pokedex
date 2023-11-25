package com.sarikaya.pokedex.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.domain.usecase.UseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val useCaseWrapper: UseCaseWrapper
) : ViewModel() {

    private val _pokemonLiveData: MutableLiveData<Resource<PokemonDetailDto>> = MutableLiveData()
    val pokemonLiveData = _pokemonLiveData

    private val _pokemonDescLiveData: MutableLiveData<Resource<String>> = MutableLiveData()
    val pokemonDescLiveData = _pokemonDescLiveData

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading = _isLoading

    fun getPokemon(pokeName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            useCaseWrapper.getPokemonDetailUseCase.invoke(pokeName).let {
                    _pokemonLiveData.postValue(it)
                }
            useCaseWrapper.getPokemonDescriptionUseCase.invoke(pokeName).let {
                _pokemonDescLiveData.postValue(it)
            }
            _isLoading.postValue(false)
        }
    }
}