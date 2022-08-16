package com.roam.diegogutierrez.wheelycool.data.database

import com.roam.diegogutierrez.data.source.LocalDataSource
import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.wheelycool.data.toDatabaseSpinItem
import com.roam.diegogutierrez.wheelycool.data.toDomainSpinItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomDataSource(db: WheelyCoolDatabase): LocalDataSource {

    private val spinItemDao = db.spinItemsDao()

    override suspend fun saveSpinItem(spinItem: SpinItem) = withContext(Dispatchers.IO) {
        spinItemDao.saveSpinItem(spinItem.toDatabaseSpinItem())
    }

    override suspend fun deleteSpinItem(spinItem: SpinItem) {
        spinItemDao.deleteSpinItem(spinItem.id)
    }

    override fun getSpinItems(): Flow<List<SpinItem>> = spinItemDao.getSpinItems().map { items ->
        items.map { it.toDomainSpinItem() }
    }
}