package com.teddy.brunch.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teddy.brunch.R
import com.teddy.brunch.base.BaseAdapter
import com.teddy.brunch.databinding.RestaurantItemListBinding
import com.teddy.business.home.RestaurantView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RestaurantListAdapter :
    BaseAdapter<RestaurantView, RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder =
        RestaurantViewHolder(
            RestaurantItemListBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.restaurant_item_list,
                    parent,
                    false
                )
            ), onClickListener
        )

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

@ExperimentalCoroutinesApi
class RestaurantViewHolder(
    private val binding: RestaurantItemListBinding,
    private val listener: ((RestaurantView) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivVideoThumbnail.clipToOutline = true
    }

    fun bind(item: RestaurantView) {
        binding.restaurant = item
        binding.root.setOnClickListener {
            listener?.invoke(item)
        }
    }
}
