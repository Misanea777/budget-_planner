package com.endava.internship.mobile.budgetplanner.ui.auth.register.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentSignUpBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val args: SignUpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        args.userRegistrationInfo?.let { signUpViewModel.setData(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = signUpViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.toSignInButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            findNavController().popBackStack()
        }

        signUpViewModel.isReadyToContinue.observe(viewLifecycleOwner) { isReadyToContinue ->
            val action = SignUpFragmentDirections.actionSignUpFragmentToOnboardingStepOneFragment(
                signUpViewModel.userRegistrationInfo
            )
            if (isReadyToContinue) findNavController().navigate(action)
        }

        signUpViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage ->
            statusMessage.getContentIfNotHandled()?.let {
                showErrorDialog(this.getString(R.string.error_general), it)
            }
        }

        signUpViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingDialogSetVisible(isLoading)
        }
    }
}
