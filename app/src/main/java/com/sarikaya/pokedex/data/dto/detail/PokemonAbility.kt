package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    @SerializedName("name") val name: String
)