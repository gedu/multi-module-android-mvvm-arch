package com.teddy.brunch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.teddy.brunch.base.extensions.navigateSafely

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected lateinit var binding: T
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onCreateSetup()

        return binding.root
    }

    protected open fun onCreateSetup() {}

    protected fun navigateUp() {
        findNavController().navigateUp()
    }

    protected fun navigateTo(resId: Int, args: Bundle? = null) {
        findNavController().navigateSafely(resId, args)
    }

    protected fun navigateTo(navDirections: NavDirections) {
        findNavController().navigateSafely(navDirections)
    }
}