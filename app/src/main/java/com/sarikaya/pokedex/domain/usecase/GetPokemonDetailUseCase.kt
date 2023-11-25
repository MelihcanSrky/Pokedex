package com.sarikaya.pokedex.domain.usecase

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.domain.repository.PokedexRepository

class GetPokemonDetailUseCase(private val repository: PokedexRepository) {
    suspend operator fun invoke(pokeName: String) : Resource<PokemonDetailDto> {
        return repository.getPokemonDetail(pokeName)
    }
}