package com.teddy.brunch.restaurantdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teddy.brunch.R
import com.teddy.brunch.base.BaseAdapter
import com.teddy.brunch.databinding.MenuItemListBinding
import com.teddy.common.model.BasicMenu
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuPriceListAdapter :
    BaseAdapter<BasicMenu, MenuPriceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuPriceViewHolder =
        MenuPriceViewHolder(
            MenuItemListBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.menu_item_list,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: MenuPriceViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

@ExperimentalCoroutinesApi
class MenuPriceViewHolder(
    private val binding: MenuItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BasicMenu) {
        binding.menuPrice = item
    }
}