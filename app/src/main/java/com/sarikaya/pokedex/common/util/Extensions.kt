package com.sarikaya.pokedex.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sarikaya.pokedex.R
import com.sarikaya.pokedex.common.Constants.PICS_URL
import com.sarikaya.pokedex.data.dto.list.PokemonListDto
import com.sarikaya.pokedex.data.dto.PokemonSearchDto
import com.sarikaya.pokedex.domain.model.PokemonList
import com.sarikaya.pokedex.domain.model.PokemonListItem

fun Resource<PokemonListDto>.convertIntoPokemonList(): Resource<PokemonList> {
    this.data.let { data ->
        var list: MutableList<PokemonListItem> = mutableListOf()
        data!!.results.forEach { item ->
            val number = if (item.url.endsWith("/")) {
                item.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                item.url.takeLastWhile { it.isDigit() }
            }
            val url =
                "${PICS_URL}${number}.png"
            val item = PokemonListItem(
                name = item.name.replaceFirstChar { it.uppercaseChar() },
                number = "#${"%03d".format(number.toInt())}",
                imageUrl = url
            )
            list.add(item)
        }
        return Resource.Success(
            data = PokemonList(
                count = data.count,
                next = data.next,
                previous = data.previous,
                results = list
            )
        )
    }
}

fun Resource<PokemonSearchDto>.convertIntoPokeItem(): Resource<PokemonList> {
    this.data?.let { data ->
        return Resource.Success(
            data = PokemonList(
                count = 1,
                next = null,
                previous = null,
                results = listOf(
                    PokemonListItem(
                        name = data.name,
                        number = "#${"%03d".format(data.id)}",
                        imageUrl = "${PICS_URL}${data.id}.png"
                    )
                )
            )
        )
    }
    return Resource.Error(
        message = this.message ?: "Unknown error!"
    )
}

fun ImageView.setImage(url: String) {
    Glide.with(this).load(url)
        .placeholder(R.drawable.image)
        .into(this)
}

fun String.convertToPokeId() : String {
    return this.replace("#", "").replace("^0+".toRegex(), "")
}

fun String.filterDigits() : String {
    return this.filter { !it.isDigit() }
}

fun String.returnColorResource() : Int {
    return when(this) {
        "bug" -> R.color.bug
        "dark" -> R.color.dark
        "dragon" -> R.color.dragon
        "electric" -> R.color.electric
        "fairy" -> R.color.fairy
        "fighting" -> R.color.fighting
        "fire" -> R.color.fire
        "flying" -> R.color.flying
        "ghost" -> R.color.ghost
        "normal" -> R.color.normal
        "grass" -> R.color.grass
        "ground" -> R.color.ground
        "ice" -> R.color.ice
        "poison" -> R.color.poison
        "psychic" -> R.color.psychic
        "rock" -> R.color.rock
        "steel" -> R.color.steel
        "water" -> R.color.water
        else -> R.color.wireframe
    }
}

fun parseStatToAbbr(stat: String): String {
    return when(stat.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SATK"
        "special-defense" -> "SDEF"
        "speed" -> "SPD"
        else -> ""
    }
}