package com.teddy.brunch.restaurantdetails

import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.teddy.brunch.R
import com.teddy.brunch.base.BaseFragment
import com.teddy.brunch.databinding.FragmentRestaurantDetailsBinding
import com.teddy.business.restaurantdetails.RestaurantDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class RestaurantDetailsFragment : BaseFragment<FragmentRestaurantDetailsBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_restaurant_details

    private val viewModel: RestaurantDetailsViewModel by viewModel()
    private val args: RestaurantDetailsFragmentArgs by navArgs()
    private val diaporamaAdapter = DiaporamaHeaderListAdapter()
    private val menuPriceAdapter = MenuPriceListAdapter()

    override fun onCreateSetup() {
        super.onCreateSetup()
        binding.backButton.setOnClickListener {
            navigateUp()
        }
        viewModel.restaurantInfo.observe(viewLifecycleOwner, Observer { restaurant ->
            binding.restaurant = restaurant
            restaurant?.diaporamaPics?.let { pics ->
                diaporamaAdapter.update(pics)
            }
            restaurant?.menu?.let { items ->
                menuPriceAdapter.update(items)
            }
        })
        binding.imageSlider.setSliderAdapter(diaporamaAdapter)
        binding.menuListContainer.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuPriceAdapter
        }
        viewModel.getRestaurantInfoBy(args.restaurantId)
    }
}