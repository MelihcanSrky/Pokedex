package com.sarikaya.pokedex.domain.usecase

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.common.util.convertIntoPokemonList
import com.sarikaya.pokedex.domain.model.PokemonList
import com.sarikaya.pokedex.domain.repository.PokedexRepository

class GetPokemonListUseCase(private val repository: PokedexRepository) {
    suspend operator fun invoke(offset: Int) : Resource<PokemonList> {
        return repository.getPokemonList(offset).convertIntoPokemonList()
    }
}