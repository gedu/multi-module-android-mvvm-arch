package com.teddy.brunch.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.teddy.brunch.R
import com.teddy.brunch.base.BaseFragment
import com.teddy.brunch.databinding.FragmentHomeBinding
import com.teddy.business.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModel()
    private val adapter = RestaurantListAdapter()

    override fun onCreateSetup() {
        super.onCreateSetup()
        binding.viewModel = viewModel
        setupRestaurantView()
    }

    private fun setupRestaurantView() {
        binding.restaurantList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        adapter.onClickListener = { restaurantView ->
            navigateTo(
                HomeFragmentDirections.goToRestaurantDetails(restaurantView.idRestaurant)
            )
        }

        viewModel.loadingError.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                binding.errorLabel.setText(
                    if (it == HomeViewModel.WrongState.EMPTY)
                        R.string.empty_restaurants
                    else
                        R.string.load_restaurants_error
                )
            }
        })

        viewModel.restaurants.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
    }
}