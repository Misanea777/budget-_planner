package com.endava.internship.mobile.budgetplanner.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()

        splashViewModel.load()
        splashViewModel.isLoaded.observe(viewLifecycleOwner) { isLoaded->
            if(isLoaded) findNavController().navigate(action)
        }
    }
}
