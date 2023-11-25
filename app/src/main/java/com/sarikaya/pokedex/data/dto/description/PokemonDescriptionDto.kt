package com.sarikaya.pokedex.data.dto.description

import com.google.gson.annotations.SerializedName

data class PokemonDescriptionDto(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntries>
)