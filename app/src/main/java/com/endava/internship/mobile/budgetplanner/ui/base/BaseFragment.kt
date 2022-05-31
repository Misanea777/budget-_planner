package com.endava.internship.mobile.budgetplanner.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.endava.internship.mobile.budgetplanner.ui.dialogs.ErrorDialog
import com.endava.internship.mobile.budgetplanner.ui.dialogs.LoadingRequestDialog
import com.endava.internship.mobile.budgetplanner.util.Constants

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private lateinit var _binding: VB

    val binding: VB
        get() = _binding

    protected val loadingRequestDialog = LoadingRequestDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    fun loadingDialogSetVisible(visible: Boolean) {
        if (visible) loadingRequestDialog.show(
            childFragmentManager,
            Constants.DialogTags.SIGN_IN_LOADING
        ) else loadingRequestDialog.dismiss()
    }

    fun showErrorDialog(title: String, message: String) {
        ErrorDialog(
            title,
            message
        ).show(childFragmentManager, Constants.DialogTags.SIGN_IN_FAILED)
    }
}
