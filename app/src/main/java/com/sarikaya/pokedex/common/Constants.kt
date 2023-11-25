package com.sarikaya.pokedex.common

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"

    const val PAGE_LIMIT = 18

    const val PICS_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

    enum class SearchBy {
        NUMBER,
        NAME
    }
}