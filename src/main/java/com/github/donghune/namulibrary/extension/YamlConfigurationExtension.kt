package com.github.donghune.namulibrary.extension

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack

fun YamlConfiguration.setItemStackList(path: String, list: List<ItemStack>) {
    set("$path.count", list.size)
    list.forEachIndexed { index: Int, itemStack: ItemStack ->
        set("$path.item.$index", itemStack)
    }
}

fun YamlConfiguration.getItemStackList(path: String): List<ItemStack> {
    val count = getInt("$path.count")
    return (0 until count).map { index ->
        getItemStack("$path.item.$index")!!
    }
}