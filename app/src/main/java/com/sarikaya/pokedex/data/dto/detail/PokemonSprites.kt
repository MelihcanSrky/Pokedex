package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonSprites(
    @SerializedName("other") val other: PokemonSpritesOther?
)