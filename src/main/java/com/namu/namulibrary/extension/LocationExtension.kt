package com.namu.namulibrary.extension

import org.bukkit.Bukkit
import org.bukkit.Location

fun emptyLocation(): Location {
    return Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0, 0F, 0F)
}