package com.roam.diegogutierrez.wheelycool.ui.addSpinItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.roam.diegogutierrez.wheelycool.databinding.FragmentAddSpinItemBinding
import com.roam.diegogutierrez.wheelycool.ui.common.getScreenWidth
import com.roam.diegogutierrez.wheelycool.ui.spinItems.SpinItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSpinItemFragment: DialogFragment() {

    private lateinit var binding: FragmentAddSpinItemBinding
    private val viewModel: SpinItemsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSpinItemBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clAddSpinItem.minWidth = (getScreenWidth() * 0.75).toInt()
        binding.btnSave.setOnClickListener {
            viewModel.saveSpinItem(binding.etSpinItemTitle.text.toString())
            this.dismiss()
        }
    }
}