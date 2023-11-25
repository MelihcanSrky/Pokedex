package com.sarikaya.pokedex.data.repository

import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.data.api.PokedexApiService
import com.sarikaya.pokedex.data.dto.description.PokemonDescriptionDto
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.data.dto.list.PokemonListDto
import com.sarikaya.pokedex.data.dto.PokemonSearchDto
import com.sarikaya.pokedex.domain.repository.PokedexRepository
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val pokedexApiService: PokedexApiService
) : PokedexRepository {
    override suspend fun getPokemonList(offset: Int): Resource<PokemonListDto> {
        val response = try {
            pokedexApiService.getPokemonList(offset)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error!")
        }
        return Resource.Success(
            data = response.body()
        )
    }

    override suspend fun searchPokemon(pokeId: String): Resource<PokemonSearchDto> {
        val response = try {
            pokedexApiService.searchPokemon(pokeId)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error!")
        }
        return Resource.Success(
            data = response.body()
        )
    }

    override suspend fun getPokemonDetail(pokeName: String): Resource<PokemonDetailDto> {
        val response = try {
            pokedexApiService.getPokemonDetail(pokeName)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error!")
        }
        return Resource.Success(
            data = response.body()
        )
    }

    override suspend fun getPokemonDescription(pokeName: String): Resource<PokemonDescriptionDto> {
        val response = try {
            pokedexApiService.getPokemonDescription(pokeName)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error!")
        }
        return Resource.Success(
            data = response.body()
        )
    }

}