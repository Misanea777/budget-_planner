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

    protected var loadingRequestDialog: LoadingRequestDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    fun loadingDialogSetVisible(visible: Boolean) {
        childFragmentManager.executePendingTransactions()
        if(loadingRequestDialog == null) loadingRequestDialog = LoadingRequestDialog()
        loadingRequestDialog?.let {
            if (!it.isAdded && visible) it.show(
                childFragmentManager,
                Constants.DialogTags.SIGN_IN_LOADING
            ) else it.dismiss()
        }
    }

    fun showErrorDialog(title: String, message: String) {
        ErrorDialog(
            title,
            message
        ).show(childFragmentManager, Constants.DialogTags.SIGN_IN_FAILED)
    }
}
