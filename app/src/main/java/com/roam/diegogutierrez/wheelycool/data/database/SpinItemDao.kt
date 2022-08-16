package com.roam.diegogutierrez.wheelycool.data.database

import androidx.room.*
import com.roam.diegogutierrez.wheelycool.data.database.SpinItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SpinItemDao {
    @Query("SELECT * FROM SpinItem")
    fun getSpinItems(): Flow<List<SpinItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSpinItem(spinItem: SpinItem)

    @Query("DELETE FROM SpinItem WHERE id = :spinItemId")
    fun deleteSpinItem(spinItemId: String)

}