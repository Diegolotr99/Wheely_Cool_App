package com.roam.diegogutierrez.usecases

import com.roam.diegogutierrez.data.repository.SpinItemsRepository
import com.roam.diegogutierrez.domain.SpinItem

class DeleteSpinItem(private val spinItemsRepository: SpinItemsRepository) {
    suspend fun invoke(spinItem: SpinItem) = spinItemsRepository.deleteSpinItem(spinItem)
}