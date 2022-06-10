package com.endava.internship.mobile.budgetplanner.ui.transaction

import android.view.LayoutInflater
import android.view.View
import com.endava.internship.mobile.budgetplanner.databinding.LayoutChipChoiceBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TransactionChipGroupViewAdapter(
    val layoutInflater: LayoutInflater,
    private val chipGroup: ChipGroup,
    var dataSet: List<TransactionCategoryModel> = listOf()
) {

    abstract class ViewHolder(val itemView: View)

    fun updateDataSet(newDataSet: List<TransactionCategoryModel>) {
        dataSet = newDataSet
        updateAllChips()
    }

    private fun updateAllChips() {
        chipGroup.removeAllViews()
        dataSet.indices.forEach { addChip(it) }
    }

    private fun addChip(position: Int) {
        val holder = onCreateViewHolder(dataSet[position].id)
        onBindViewHolder(holder, position)
        chipGroup.addView(holder.itemView, chipGroup.childCount - 1)
    }

    private fun onCreateViewHolder(viewId: Int): TransactionCategoryViewHolder {
        val binding = LayoutChipChoiceBinding.inflate(layoutInflater)
        (binding.root as Chip).id = viewId
        return TransactionCategoryViewHolder(binding)
    }

    private fun onBindViewHolder(holder: TransactionCategoryViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}
