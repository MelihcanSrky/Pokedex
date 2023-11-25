package com.sarikaya.pokedex.domain.usecase

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.domain.repository.PokedexRepository

class GetPokemonDescriptionUseCase(private val repository: PokedexRepository) {
    suspend operator fun invoke(pokeName: String) : Resource<String> {
        try {
            val response = repository.getPokemonDescription(pokeName)
            val entries = response.data?.let {
                it.flavorTextEntries.filter { it.language.name == "en" }
            }
            val randomEntry = entries?.random()

            if(randomEntry != null) {
                return Resource.Success(data = randomEntry.flavorText.replace("\n", " "))
            } else {
                return Resource.Error("No english text available")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error!")
        }
    }
}