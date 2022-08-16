package com.roam.diegogutierrez.wheelycool.data

import com.roam.diegogutierrez.domain.SpinItem
import com.roam.diegogutierrez.wheelycool.data.database.SpinItem as DatabaseSpinItem

fun SpinItem.toDatabaseSpinItem(): DatabaseSpinItem = DatabaseSpinItem(id, title)

fun DatabaseSpinItem.toDomainSpinItem(): SpinItem = SpinItem(id, title)