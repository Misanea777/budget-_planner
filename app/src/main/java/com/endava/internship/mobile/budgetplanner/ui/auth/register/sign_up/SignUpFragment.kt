package com.endava.internship.mobile.budgetplanner.ui.auth.register.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentSignUpBinding
import com.endava.internship.mobile.budgetplanner.util.validators.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = signUpViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        signUpViewModel.isReadyToContinue.observe(viewLifecycleOwner) { isReadyToContinue ->
            val action = SignUpFragmentDirections.actionSignUpFragmentToOnboardingStepOneFragment(
                signUpViewModel.userRegistrationInfo
            )
            if (isReadyToContinue) findNavController().navigate(action)
        }
    }
}