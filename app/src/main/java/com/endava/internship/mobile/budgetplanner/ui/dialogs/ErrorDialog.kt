package com.endava.internship.mobile.budgetplanner.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.DialogFragmentErrorBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialog(private val title: String, val error: String) : DialogFragment() {

    lateinit var binding: DialogFragmentErrorBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentErrorBinding.inflate(layoutInflater)
        binding.titleText.text = title
        binding.errorText.text = error

        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.AlertDialog_Rounded_MaterialComponents_MaterialAlertDialog
        ).apply {
            setView(binding.root)
        }.create()

        binding.gotItButton.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }
}
