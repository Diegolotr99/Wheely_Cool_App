package com.roam.diegogutierrez.wheelycool.di

import android.app.Application
import androidx.room.Room
import com.roam.diegogutierrez.data.source.LocalDataSource
import com.roam.diegogutierrez.wheelycool.data.database.RoomDataSource
import com.roam.diegogutierrez.wheelycool.data.database.WheelyCoolDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        WheelyCoolDatabase::class.java,
        "wheely-cool-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: WheelyCoolDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    @Singleton
    fun coroutineDispatcherProvider(): CoroutineDispatcher = Dispatchers.Main
}