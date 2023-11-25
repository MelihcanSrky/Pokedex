package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("name") val name: String
)