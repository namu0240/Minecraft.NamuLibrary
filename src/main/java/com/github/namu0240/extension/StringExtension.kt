package com.github.namu0240.extension

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location

fun String.replaceChatColorCode(): String {
    return replace("&", "§")
}

fun String.removeChatColorCode(): String {
    return ChatColor.stripColor(this)!!
}

fun String.parseLocation(): Location {
    val data = split(",")
    return Location(Bukkit.getWorld(data[0]), data[1].toDouble(), data[2].toDouble(), data[3].toDouble())
}