package com.endava.internship.mobile.budgetplanner.ui.auth.register.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.databinding.FragmentSignUpBinding
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

        binding.toSignInButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        signUpViewModel.isReadyToContinue.observe(viewLifecycleOwner) { isReadyToContinue ->
            val action = SignUpFragmentDirections.actionSignUpFragmentToOnboardingStepOneFragment(
                signUpViewModel.userRegistrationInfo
            )
            if (isReadyToContinue) findNavController().navigate(action)
        }
    }
}
