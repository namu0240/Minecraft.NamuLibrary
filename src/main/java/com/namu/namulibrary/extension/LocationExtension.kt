package com.namu.namulibrary.extension

import org.bukkit.Bukkit
import org.bukkit.Location

fun emptyLocation(): Location {
    return Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0, 0F, 0F)
}

fun Location.addX(x: Double) {
    add(x, 0.0, 0.0)
}

fun Location.addY(y: Double) {
    add(0.0, y, 0.0)
}

fun Location.addZ(z: Double) {
    add(0.0, 0.0, z)
}