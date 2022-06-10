package com.endava.internship.mobile.budgetplanner.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.DialogFragmentSuccessTransactionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SuccessTransactionDialog(
    private val title: String,
    var message: String,
    private val onDone: () -> Unit,
    private val onAddAnother: () -> Unit
) : DialogFragment() {
    lateinit var binding: DialogFragmentSuccessTransactionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentSuccessTransactionBinding.inflate(layoutInflater)
        binding.titleText.text = title
        binding.messageText.text = message

        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.AlertDialog_Rounded_MaterialComponents_MaterialAlertDialog
        ).apply {
            setView(binding.root)
        }.create()

        binding.doneButton.setOnClickListener {
            dialog.dismiss()
            onDone.invoke()
        }

        binding.addAnotherButton.setOnClickListener {
            dialog.dismiss()
            onAddAnother.invoke()
        }

        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun setMsg(msg: String) {
        message = msg
        binding.messageText.text = message
    }
}
