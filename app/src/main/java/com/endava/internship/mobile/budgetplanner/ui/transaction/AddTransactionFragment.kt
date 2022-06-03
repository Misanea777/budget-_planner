package com.endava.internship.mobile.budgetplanner.ui.transaction

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.ActivityMainBinding.inflate
import com.endava.internship.mobile.budgetplanner.databinding.FragmentAddTransactionBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTransactionFragment :
    BaseFragment<FragmentAddTransactionBinding>(FragmentAddTransactionBinding::inflate) {

    private val addTransactionViewModel by viewModels<AddTransactionViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = addTransactionViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToDashboardFragment())
        }

        addTransactionViewModel.isExpensesTransaction.observe(viewLifecycleOwner) { isExpensesTransaction ->
            binding.chipGroup.removeAllViews()
            if (isExpensesTransaction) {
                addTransactionViewModel.expensesCategories.observe(viewLifecycleOwner) { expensesCategories ->
                    expensesCategories.forEach { addChip(it.name, it.getBackgroundColorAsInt()) }
                }
            } else {
                addTransactionViewModel.incomeCategories.observe(viewLifecycleOwner) { incomeCategories ->
                    incomeCategories.forEach { addChip(it.name, it.getBackgroundColorAsInt()) }
                }
            }

        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            addTransactionViewModel.selectedCategoryName =
                group.findViewById<Chip>(checkedIds.first()).text.toString()
        }

        addTransactionViewModel.getCategories()

    }

    private fun addChip(text: String, color: Int) {
        val colorList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_enabled)
            ),
            intArrayOf(
                color,
                color,
                ColorUtils.setAlphaComponent(color, 128)
            )
        )

        val newChip =
            LayoutInflater.from(this.context).inflate(R.layout.layout_chip_choice, null) as Chip
        newChip.text = text
        newChip.isChecked = false
        newChip.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        newChip.chipBackgroundColor = colorList
        binding.chipGroup.addView(newChip, binding.chipGroup.childCount - 1)
    }
}
