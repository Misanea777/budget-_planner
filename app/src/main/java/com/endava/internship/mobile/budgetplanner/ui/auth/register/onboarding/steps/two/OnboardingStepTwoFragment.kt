package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentOnboardingStepTwoBinding
import com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.one.OnboardingStepOneFragmentArgs
import com.endava.internship.mobile.budgetplanner.util.validators.Constants
import com.endava.internship.mobile.budgetplanner.util.validators.DecimalDigitsInputFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingStepTwoFragment : Fragment() {

    lateinit var binding: FragmentOnboardingStepTwoBinding
    private val onboardingStepTwoViewModel by viewModels<OnboardingStepTwoViewModel>()
    private val args: OnboardingStepTwoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingStepTwoBinding.inflate(layoutInflater)

        args.userRegistrationInfo?.let { onboardingStepTwoViewModel.userRegistrationInfo = it }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = onboardingStepTwoViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.initialBalanceEditText.filters = arrayOf(DecimalDigitsInputFilter(8, 2))

        onboardingStepTwoViewModel.getIndustries()

        onboardingStepTwoViewModel.industries.observe(viewLifecycleOwner) { industries ->
            val asList: MutableList<String> = industries.map { it.name }.toMutableList().apply {
                add(0, "My role is..")
            }

            val adapter = ArrayAdapter(requireContext(), R.layout.role_spinner_item, asList)
            binding.roleSpinner.adapter = adapter
        }

        onboardingStepTwoViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        }

        onboardingStepTwoViewModel.isSignedUp.observe(viewLifecycleOwner) { isSignedUp ->
            val action =
                OnboardingStepTwoFragmentDirections.actionOnboardingStepTwoFragmentToWelcomeFragment()
            if (isSignedUp) findNavController().navigate(action)
        }
    }
}