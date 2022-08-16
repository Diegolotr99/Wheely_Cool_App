package com.roam.diegogutierrez.wheelycool.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpinItem(@PrimaryKey val id: String, val title: String)