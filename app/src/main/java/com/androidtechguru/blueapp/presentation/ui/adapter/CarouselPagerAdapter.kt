package com.androidtechguru.blueapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.androidtechguru.blueapp.databinding.CarouselPagerLayoutBinding
import com.androidtechguru.blueapp.domain.model.Image
import com.androidtechguru.blueapp.presentation.utils.convertURLToDrawable

class CarouselPagerAdapter(
    private val images: List<Image>
) : RecyclerView.Adapter<CarouselPagerAdapter.CarouselViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = CarouselPagerLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarouselViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }
    
    override fun getItemCount(): Int = images.size
    
    inner class CarouselViewHolder(private val binding: CarouselPagerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.carouselIv.setImageResource(image.url.convertURLToDrawable())
        }
    }
}
