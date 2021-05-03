package com.teddy.brunch.restaurantdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import com.teddy.brunch.R
import com.teddy.brunch.databinding.DiaporamaItemListBinding
import com.teddy.common.model.DiaporamaPic
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DiaporamaHeaderListAdapter :
    SliderViewAdapter<DiaporamaImageViewHolder>() {

    private var diaporamaList = listOf<DiaporamaPic>()

    override fun onCreateViewHolder(parent: ViewGroup): DiaporamaImageViewHolder =
        DiaporamaImageViewHolder(
            DiaporamaItemListBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.diaporama_item_list,
                    parent,
                    false
                )
            )
        )

    override fun getCount(): Int = diaporamaList.size

    override fun onBindViewHolder(holder: DiaporamaImageViewHolder, position: Int) {
        holder.bind(diaporamaList[position])
    }

    fun update(items: List<DiaporamaPic>) {
        diaporamaList = items
        notifyDataSetChanged()
    }
}


@ExperimentalCoroutinesApi
class DiaporamaImageViewHolder(
    private val binding: DiaporamaItemListBinding
) : SliderViewAdapter.ViewHolder(binding.root) {

    fun bind(item: DiaporamaPic) {
        binding.diaporama = item
    }
}