package com.roam.diegogutierrez.wheelycool.ui.spinItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.roam.diegogutierrez.wheelycool.R
import com.roam.diegogutierrez.wheelycool.databinding.FragmentSpinItemsBinding
import com.roam.diegogutierrez.wheelycool.ui.common.state.SpinItemsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SpinItemsFragment : Fragment() {

    private val viewModel: SpinItemsViewModel by viewModels()
    private lateinit var binding: FragmentSpinItemsBinding
    private lateinit var adapter: SpinItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinItemsBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { uiState ->
                    when (uiState) {
                        is SpinItemsState.Success -> {
                            adapter.spinItems = uiState.data
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SpinItemsAdapter()
        binding.rvSpinItems.adapter = adapter
        binding.btnDone.setOnClickListener { navigateToSpinWheel() }
        binding.fabAddSpinItem.setOnClickListener { showAddSpinItem() }

        // Swipe Left to delete spin item
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.removeSpinItem(adapter.spinItems[position])
            }
        }).attachToRecyclerView(binding.rvSpinItems)
    }

    private fun navigateToSpinWheel() {
        findNavController().navigate(R.id.action_spinItemsFragment_to_spinWheelFragment)
    }

    private fun showAddSpinItem() {
        findNavController().navigate(R.id.action_spinItemsFragment_to_addSpinFragment)
    }
}