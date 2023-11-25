package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("types") val types: List<PokemonTypes>,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("abilities") val abilities: List<PokemonAbilities>?,
    @SerializedName("stats") val stats: List<PokemonStats>,
    @SerializedName("sprites") val sprites: PokemonSprites
)