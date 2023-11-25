package com.sarikaya.pokedex.data.dto

import com.google.gson.annotations.SerializedName
import com.sarikaya.pokedex.common.Constants.PICS_URL
import com.sarikaya.pokedex.common.util.Resource
import com.sarikaya.pokedex.domain.model.PokemonListItem

data class PokemonSearchDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)