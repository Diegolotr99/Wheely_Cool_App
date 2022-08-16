package com.roam.diegogutierrez.data.repository

import com.roam.diegogutierrez.data.source.LocalDataSource
import com.roam.diegogutierrez.domain.SpinItem
import kotlinx.coroutines.flow.Flow

class SpinItemsRepository(private val localDataSource: LocalDataSource) {

    fun getSpinItems(): Flow<List<SpinItem>> = localDataSource.getSpinItems()

    suspend fun saveSpinItem(spinItem: SpinItem) {
        localDataSource.saveSpinItem(spinItem)
    }

    suspend fun deleteSpinItem(spinItem: SpinItem) {
        localDataSource.deleteSpinItem(spinItem)
    }
}