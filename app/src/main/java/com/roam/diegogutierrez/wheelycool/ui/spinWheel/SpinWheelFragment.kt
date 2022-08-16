package com.roam.diegogutierrez.wheelycool.ui.spinWheel

import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.roam.diegogutierrez.wheelycool.databinding.FragmentSpinWheelBinding
import com.roam.diegogutierrez.wheelycool.ui.common.state.SpinItemsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SpinWheelFragment : Fragment() {
    private val viewModel: SpinWheelViewModel by viewModels()
    private lateinit var binding: FragmentSpinWheelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinWheelBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { uiState ->
                    when (uiState) {
                        is SpinItemsState.Success -> {
                            binding.swcView.setData(uiState.data)
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSpin.setOnClickListener { binding.swcView.startSpinWheelWithRandomTarget() }
    }
}