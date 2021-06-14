package com.github.donghune.namulibrary.extension

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.File


fun List<ItemStack>.toCloneTypeArray(): Array<ItemStack> {
    return map { it.clone() }.toTypedArray()
}

fun List<String>.toMap(separator: String): MutableMap<String, Int> {
    val resultMap = mutableMapOf<String, Int>()
    forEach { item ->
        val data = item.split(separator)
        resultMap[data[0]] = data[1].toInt()
    }
    return resultMap
}

fun Array<ItemStack?>.emptyCount(): Int {
    return count { it == null || it.type == Material.AIR }
}

fun File.getYmlFileList(): List<File> {
    mkdirs()
    val filesList = listFiles { _, name -> name.endsWith(".yml") } ?: return listOf()
    return filesList.toList()
}

fun List<String>.join(from: Int, to: Int): String {
    val stringBuilder = StringBuilder()
    for (i in from..to) {
        stringBuilder.append(get(i)).append(" ")
    }
    return stringBuilder.toString().run { substring(0, length - 1) }
}