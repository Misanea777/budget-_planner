package com.endava.internship.mobile.budgetplanner.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.DialogFragmentRequestLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingRequestDialog : DialogFragment() {

    lateinit var binding: DialogFragmentRequestLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentRequestLoadingBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.AlertDialog_Rounded_MaterialComponents_MaterialAlertDialog
        ).apply {
            setView(binding.root)
        }.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}
