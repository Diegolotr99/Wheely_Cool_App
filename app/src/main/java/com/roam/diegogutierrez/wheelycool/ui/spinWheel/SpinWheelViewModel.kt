package com.roam.diegogutierrez.wheelycool.ui.spinWheel

import androidx.lifecycle.viewModelScope
import com.roam.diegogutierrez.usecases.GetSpinItems
import com.roam.diegogutierrez.wheelycool.ui.common.ScopedViewModel
import com.roam.diegogutierrez.wheelycool.ui.common.state.SpinItemsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpinWheelViewModel @Inject constructor(private val getSpinItems: GetSpinItems):
    ScopedViewModel() {

    private val _screenState = MutableStateFlow<SpinItemsState>(SpinItemsState.Success(emptyList()))
    val screenState: StateFlow<SpinItemsState> get() = _screenState

    init {
        loadSpinItems()
    }

    private fun loadSpinItems() = viewModelScope.launch {
        getSpinItems.invoke().collectLatest {
            _screenState.value = SpinItemsState.Success(it)
        }
    }

    companion object {
        const val TAG = "SpinWheelViewModel"
    }
}