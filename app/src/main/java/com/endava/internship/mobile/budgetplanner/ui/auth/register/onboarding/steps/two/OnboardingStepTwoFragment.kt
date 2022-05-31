package com.endava.internship.mobile.budgetplanner.ui.auth.register.onboarding.steps.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentOnboardingStepTwoBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.util.DecimalDigitsInputFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingStepTwoFragment : BaseFragment<FragmentOnboardingStepTwoBinding>(
    FragmentOnboardingStepTwoBinding::inflate
) {

    private val onboardingStepTwoViewModel by viewModels<OnboardingStepTwoViewModel>()
    private val args: OnboardingStepTwoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        args.userRegistrationInfo?.let { onboardingStepTwoViewModel.setData(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = onboardingStepTwoViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.initialBalanceEditText.filters = arrayOf(DecimalDigitsInputFilter(8, 2))

        onboardingStepTwoViewModel.getIndustries()

        initObservers()

    }

    private fun initObservers() {
        onboardingStepTwoViewModel.industries.observe(viewLifecycleOwner) { industries ->
            val asList: MutableList<String> = industries.map { it.name }.toMutableList()

            val adapter = ArrayAdapter(requireContext(), R.layout.role_spinner_item, asList)
            (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }

        onboardingStepTwoViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        onboardingStepTwoViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }

        onboardingStepTwoViewModel.isSignedUp.observe(viewLifecycleOwner) { isSignedUp ->
            val action =
                OnboardingStepTwoFragmentDirections.actionOnboardingStepTwoFragmentToWelcomeFragment()
            if (isSignedUp) findNavController().navigate(action)
        }

        binding.backButton.setOnClickListener {
            onboardingStepTwoViewModel.saveData()
            val action =
                OnboardingStepTwoFragmentDirections.actionOnboardingStepTwoFragmentToOnboardingStepOneFragment(
                    onboardingStepTwoViewModel.userRegistrationInfo
                )
            findNavController().navigate(action)
        }

        binding.autoCompleteTxt.setOnItemClickListener { _, _, position, _ ->
            onboardingStepTwoViewModel.industry.value = position
        }

        binding.autoCompleteTxt.setText(
            onboardingStepTwoViewModel.userRegistrationInfo.industry?.name,
            false
        )
    }
}
