package com.roam.diegogutierrez.wheelycool.ui.spinItems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.usecases.DeleteSpinItem
import com.roam.diegogutierrez.usecases.GetSpinItems
import com.roam.diegogutierrez.usecases.SaveSpinItem
import com.roam.diegogutierrez.wheelycool.ui.common.state.SpinItemsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SpinItemsViewModel @Inject constructor(
    private val getSpinItems: GetSpinItems,
    private val saveSpinItem: SaveSpinItem,
    private val deleteSpinItem: DeleteSpinItem,
) : ViewModel() {

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

    fun saveSpinItem(spinItemTitle: String) = viewModelScope.launch {
        saveSpinItem.invoke(SpinItem(UUID.randomUUID().toString(), spinItemTitle))
    }

    fun removeSpinItem(spinItem: SpinItem) = viewModelScope.launch(Dispatchers.IO) {
        deleteSpinItem.invoke(spinItem)
    }

    companion object {
        const val TAG = "SpinItemsViewModel"
    }
}