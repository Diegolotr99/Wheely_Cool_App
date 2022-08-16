package com.roam.diegogutierrez.wheelycool.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SpinItem::class], version = 1, exportSchema = false)
abstract class WheelyCoolDatabase: RoomDatabase() {

    abstract fun spinItemsDao(): SpinItemDao
}