package com.roam.diegogutierrez.data.source

import com.roam.diegogutierrez.domain.SpinItem
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveSpinItem(spinItem: SpinItem)
    suspend fun deleteSpinItem(spinItem: SpinItem)
    fun getSpinItems(): Flow<List<SpinItem>>
}