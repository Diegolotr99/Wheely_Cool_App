package com.roam.diegogutierrez.usecases

import com.roam.diegogutierrez.data.repository.SpinItemsRepository
import com.roam.diegogutierrez.domain.SpinItem

class SaveSpinItem(private val spinItemsRepository: SpinItemsRepository) {
    suspend fun invoke(spinItem: SpinItem) = spinItemsRepository.saveSpinItem(spinItem)
}