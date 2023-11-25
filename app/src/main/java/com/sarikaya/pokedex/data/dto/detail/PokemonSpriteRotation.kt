package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonSpriteRotation(
    @SerializedName("front_default") val frontDefault: String
)