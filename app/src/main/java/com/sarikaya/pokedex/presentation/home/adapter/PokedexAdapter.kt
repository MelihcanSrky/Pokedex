package com.sarikaya.pokedex.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarikaya.pokedex.common.util.setImage
import com.sarikaya.pokedex.databinding.PokemonCardBinding
import com.sarikaya.pokedex.domain.model.PokemonListItem

class PokedexAdapter : RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder>() {

    private val pokemons: MutableList<PokemonListItem> = mutableListOf<PokemonListItem>()

    private val searchResults = mutableListOf<PokemonListItem>()

    private var showList = pokemons

    fun setList(data: List<PokemonListItem>) {
        if (pokemons.isEmpty()) {
            pokemons.addAll(data)
        } else if (pokemons.last().number != data.last().number) {
            pokemons.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setSearchResults(data: List<PokemonListItem>) {
        searchResults.clear()
        searchResults.addAll(data)
        notifyDataSetChanged()
    }

    fun changeShowList(isSearching: Boolean) {
        if (isSearching) {
            showList = searchResults
        } else {
            showList = pokemons
        }
        notifyDataSetChanged()
    }

    inner class PokedexViewHolder(private val binding: PokemonCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonListItem) {
            binding.numberTextView.text = item.number
            binding.pokemonImageView.setImage(item.imageUrl)
            binding.nameTextView.text = item.name
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val bind = PokemonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokedexViewHolder(bind)
    }

    override fun getItemCount(): Int = showList.size

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        holder.bind(showList[position])
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}