package com.sarikaya.pokedex.domain.usecase

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.common.util.convertIntoPokeItem
import com.sarikaya.pokedex.domain.model.PokemonList
import com.sarikaya.pokedex.domain.model.PokemonListItem
import com.sarikaya.pokedex.domain.repository.PokedexRepository

class SearchPokemonUseCase(private val repository: PokedexRepository) {
    suspend operator fun invoke(pokeId: String) : Resource<PokemonList> {
        return repository.searchPokemon(pokeId = pokeId).convertIntoPokeItem()
    }
}