package com.sarikaya.pokedex.data.dto.detail

import com.google.gson.annotations.SerializedName

data class PokemonAbilities(
    @SerializedName("ability") val ability: PokemonAbility,
    @SerializedName("slot") val slot: Int
)