package com.endava.internship.mobile.budgetplanner.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.DialogFragmentYesNoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class YesNoDialog(
    private val title: String,
    private val message: String,
    private val onYes: () -> Unit,
    private val onNo: (() -> Unit)? = null
) : DialogFragment() {
    private lateinit var binding: DialogFragmentYesNoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentYesNoBinding.inflate(layoutInflater)
        binding.titleText.text = title
        binding.messageText.text = message

        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.AlertDialog_Rounded_MaterialComponents_MaterialAlertDialog
        ).apply {
            setView(binding.root)
        }.create()

        binding.yesButton.setOnClickListener {
            dialog.dismiss()
            onYes.invoke()
        }

        binding.noButton.setOnClickListener {
            dialog.dismiss()
            onNo?.invoke()
        }

        return dialog
    }
}
