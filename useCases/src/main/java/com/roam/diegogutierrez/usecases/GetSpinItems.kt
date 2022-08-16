package com.roam.diegogutierrez.usecases

import com.roam.diegogutierrez.data.repository.SpinItemsRepository
import com.roam.diegogutierrez.domain.SpinItem
import kotlinx.coroutines.flow.Flow

class GetSpinItems(private val spinItemsRepository: SpinItemsRepository) {

    fun invoke(): Flow<List<SpinItem>> = spinItemsRepository.getSpinItems()
}