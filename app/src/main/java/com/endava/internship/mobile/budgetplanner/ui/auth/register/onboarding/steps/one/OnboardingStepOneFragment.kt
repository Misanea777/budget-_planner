package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.one

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.internship.mobile.budgetplanner.databinding.FragmentOnboardingStepOneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingStepOneFragment : Fragment() {

    lateinit var binding: FragmentOnboardingStepOneBinding
    private val onboardingStepOneViewModel by viewModels<OnboardingStepOneViewModel>()
    private val args: OnboardingStepOneFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingStepOneBinding.inflate(layoutInflater)

        args.userRegistrationInfo?.let { onboardingStepOneViewModel.userRegistrationInfo = it }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = onboardingStepOneViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        onboardingStepOneViewModel.isReadyToContinueToStepTwo.observe(viewLifecycleOwner) { isReadyToContinueToStepTwo ->
            val action = OnboardingStepOneFragmentDirections.actionOnboardingStepOneFragmentToOnboardingStepTwoFragment(
                onboardingStepOneViewModel.userRegistrationInfo
            )

            if (isReadyToContinueToStepTwo) findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
