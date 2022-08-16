package com.roam.diegogutierrez.wheelycool.di

import com.roam.diegogutierrez.data.repository.SpinItemsRepository
import com.roam.diegogutierrez.data.source.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun spinItemsRepositoryProvider(localDataSource: LocalDataSource) = SpinItemsRepository(localDataSource)
}