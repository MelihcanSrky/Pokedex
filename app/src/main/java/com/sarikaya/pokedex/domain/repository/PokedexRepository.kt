package com.sarikaya.pokedex.domain.repository

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.data.dto.description.PokemonDescriptionDto
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.data.dto.list.PokemonListDto
import com.sarikaya.pokedex.data.dto.PokemonSearchDto

interface PokedexRepository {
    suspend fun getPokemonList(offset: Int) : Resource<PokemonListDto>
    suspend fun searchPokemon(pokeId: String) : Resource<PokemonSearchDto>
    suspend fun getPokemonDetail(pokeName: String) : Resource<PokemonDetailDto>
    suspend fun getPokemonDescription(pokeName: String) : Resource<PokemonDescriptionDto>
}