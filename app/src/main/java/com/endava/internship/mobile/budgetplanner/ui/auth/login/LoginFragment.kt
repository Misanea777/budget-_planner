package com.endava.internship.mobile.budgetplanner.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.databinding.FragmentLoginBinding
import com.endava.internship.mobile.budgetplanner.ui.dialogs.FailedAuthDialog
import com.endava.internship.mobile.budgetplanner.ui.dialogs.LoadingRequestDialog
import com.endava.internship.mobile.budgetplanner.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    private val loadingRequestDialog = LoadingRequestDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initListenersAndObservers()
    }

    private fun initListenersAndObservers() {
        binding.toSignUpButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        loginViewModel.isSignedIn.observe(viewLifecycleOwner) { isSignedIn ->
            if(isSignedIn) findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
        }

        loginViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading) loadingRequestDialog.show(childFragmentManager, Constants.DialogTags.SIGN_IN_LOADING) else loadingRequestDialog.dismiss()
        }

        loginViewModel.statusMessage.observe(viewLifecycleOwner) { statusMessage->
            if (statusMessage != null) {
                if(statusMessage.isNotBlank()) FailedAuthDialog(statusMessage).show(childFragmentManager, Constants.DialogTags.SIGN_IN_FAILED)
            }
        }
    }
}
