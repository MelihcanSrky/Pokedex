package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonTypes(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: PokemonType
)