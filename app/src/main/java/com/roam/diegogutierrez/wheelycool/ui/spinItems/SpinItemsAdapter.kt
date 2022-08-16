package com.roam.diegogutierrez.wheelycool.ui.spinItems

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.wheelycool.R
import com.roam.diegogutierrez.wheelycool.databinding.CardSpinItemBinding
import com.roam.diegogutierrez.wheelycool.ui.common.basicDiffUtil
import com.roam.diegogutierrez.wheelycool.ui.common.inflate

class SpinItemsAdapter: RecyclerView.Adapter<SpinItemsAdapter.ViewHolder>() {

    var spinItems: List<SpinItem> by basicDiffUtil(
        emptyList(),
        areContentsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.card_spin_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spinItem = spinItems[position]
        holder.bind(spinItem)
    }

    override fun getItemCount(): Int = spinItems.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardSpinItemBinding.bind(view)
        fun bind(spinItem: SpinItem) {
            binding.tvSpinItemTitle.text = spinItem.title
        }
    }
}