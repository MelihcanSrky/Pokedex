package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonSpritesOther(
    @SerializedName("official-artwork") val officialArtwork: PokemonSpriteRotation
)