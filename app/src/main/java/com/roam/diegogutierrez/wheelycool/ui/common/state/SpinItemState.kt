package com.roam.diegogutierrez.wheelycool.ui.common.state

import com.roam.diegogutierrez.domain.SpinItem

sealed class SpinItemsState {
    data class Success(val data: List<SpinItem>) : SpinItemsState()
}
