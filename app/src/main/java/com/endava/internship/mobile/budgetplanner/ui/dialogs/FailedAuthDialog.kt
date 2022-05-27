package com.endava.internship.mobile.budgetplanner.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.DialogFragmentFailedAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FailedAuthDialog(val error: String) : DialogFragment() {

    lateinit var binding: DialogFragmentFailedAuthBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentFailedAuthBinding.inflate(layoutInflater)
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
