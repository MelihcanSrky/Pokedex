package com.sarikaya.pokedex.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarikaya.pokedex.common.util.returnColorResource
import com.sarikaya.pokedex.data.dto.detail.PokemonTypes
import com.sarikaya.pokedex.databinding.TypeChipBinding

class TypeChipAdapter : RecyclerView.Adapter<TypeChipAdapter.TypeViewHolder>() {

    private var types = mutableListOf<PokemonTypes>()

    inner class TypeViewHolder(private val binding: TypeChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonTypes) {
            binding.typeChip.text = item.type.name.replaceFirstChar {
                it.uppercase()
            }
            binding.typeChip.setChipBackgroundColorResource(item.type.name.returnColorResource())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val bind = TypeChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(types[position])
    }

    fun setChips(data: List<PokemonTypes>) {
        types.clear()
        types.addAll(data)
        notifyDataSetChanged()
    }
}