package com.sarikaya.pokedex.data.api

import com.sarikaya.pokedex.common.Constants.PAGE_LIMIT
import com.sarikaya.pokedex.data.dto.description.PokemonDescriptionDto
import com.sarikaya.pokedex.data.dto.detail.PokemonDetailDto
import com.sarikaya.pokedex.data.dto.list.PokemonListDto
import com.sarikaya.pokedex.data.dto.PokemonSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApiService {
    @GET("pokemon/?limit=$PAGE_LIMIT")
    suspend fun getPokemonList(
        @Query("offset") offset: Int
    ) : Response<PokemonListDto>

    @GET("pokemon/{pokeId}")
    suspend fun searchPokemon(
        @Path("pokeId") pokeId: String
    ) : Response<PokemonSearchDto>

    @GET("pokemon/{pokeName}")
    suspend fun getPokemonDetail(
        @Path("pokeName") pokeName: String
    ) : Response<PokemonDetailDto>

    @GET("pokemon-species/{pokeName}")
    suspend fun getPokemonDescription(
        @Path("pokeName") pokeName: String
    ) : Response<PokemonDescriptionDto>
}