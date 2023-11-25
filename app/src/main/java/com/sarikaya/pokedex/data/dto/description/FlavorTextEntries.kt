package com.sarikaya.pokedex.data.dto.description

import com.google.gson.annotations.SerializedName

data class FlavorTextEntries(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: Language
)