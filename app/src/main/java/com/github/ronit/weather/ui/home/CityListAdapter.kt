package com.github.ronit.weather.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ronit.weather.databinding.CityListItemBinding

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {




    inner class CityViewHolder(val binding: CityListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding: CityListItemBinding = CityListItemBinding.inflate(LayoutInflater.from(parent.context))
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.binding.cityName.text = "City $position"
    }

    override fun getItemCount() = 10
}