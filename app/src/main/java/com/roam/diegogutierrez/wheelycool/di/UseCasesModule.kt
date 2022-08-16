package com.roam.diegogutierrez.wheelycool.di

import com.roam.diegogutierrez.data.repository.SpinItemsRepository
import com.roam.diegogutierrez.usecases.DeleteSpinItem
import com.roam.diegogutierrez.usecases.GetSpinItems
import com.roam.diegogutierrez.usecases.SaveSpinItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun getSpinItemsProvider(spinItemsRepository: SpinItemsRepository) = GetSpinItems(spinItemsRepository)

    @Provides
    @ViewModelScoped
    fun saveSpinItemsProvider(spinItemsRepository: SpinItemsRepository) = SaveSpinItem(spinItemsRepository)


    @Provides
    @ViewModelScoped
    fun deleteSpinItemProvider(spinItemsRepository: SpinItemsRepository) = DeleteSpinItem(spinItemsRepository)

}