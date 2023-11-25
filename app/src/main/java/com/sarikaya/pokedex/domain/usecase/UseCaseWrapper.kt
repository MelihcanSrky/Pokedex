package com.sarikaya.pokedex.domain.usecase

data class UseCaseWrapper(
    val getPokemonListUseCase : GetPokemonListUseCase,
    val searchPokemonUseCase: SearchPokemonUseCase,
    val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase
)