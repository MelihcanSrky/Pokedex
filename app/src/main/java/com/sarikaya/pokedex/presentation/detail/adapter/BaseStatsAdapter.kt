package com.sarikaya.pokedex.presentation.detail.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sarikaya.pokedex.common.util.parseStatToAbbr
import com.sarikaya.pokedex.common.util.returnColorResource
import com.sarikaya.pokedex.data.dto.detail.PokemonStats
import com.sarikaya.pokedex.databinding.BaseStatsProgressBinding

class BaseStatsAdapter : RecyclerView.Adapter<BaseStatsAdapter.BaseStatsViewHolder>() {

    var color: Int? = null
    val stats = mutableListOf<PokemonStats>()

    inner class BaseStatsViewHolder(private val binding: BaseStatsProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonStats) {
            binding.baseStatNameText.text = parseStatToAbbr(item.stat.name)
            color?.let {
                binding.baseStatNameText.setTextColor(
                    ContextCompat.getColor(
                        this.itemView.context,
                        color!!
                    )
                )
                binding.baseStatProgressBar.setIndicatorColor(
                    ContextCompat.getColor(
                        this.itemView.context,
                        color!!
                    )
                )
                var trackColor = ContextCompat.getColor(this.itemView.context, color!!)
                val trackWithAlpha = Color.argb(
                    51,
                    Color.red(trackColor),
                    Color.green(trackColor),
                    Color.blue(trackColor)
                )
                binding.baseStatProgressBar.trackColor = trackWithAlpha

            }
            binding.baseStatValueText.text = "${"%03d".format(item.baseStat)}"
            binding.baseStatProgressBar.progress = item.baseStat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStatsViewHolder {
        val bind =
            BaseStatsProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseStatsViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return stats.size
    }

    override fun onBindViewHolder(holder: BaseStatsViewHolder, position: Int) {
        holder.bind(stats[position])
    }

    fun setColor(color: String) {
        this.color = color.returnColorResource()
        notifyDataSetChanged()
    }

    fun setStats(data: List<PokemonStats>) {
        stats.clear()
        stats.addAll(data)
    }

}