package com.github.namu0240.extension

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.HashMap

fun Inventory.hasItems(items : Array<ItemStack>) : Boolean {
    val cloneInventoryContents = storageContents.clone()

    val returnedItemList: HashMap<Int, ItemStack> = removeItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        storageContents = cloneInventoryContents
        return true
    }

    storageContents = cloneInventoryContents
    return false
}

fun Inventory.takeItems(items: Array<ItemStack>): Boolean {
    val cloneInventoryContents = storageContents.clone()

    val returnedItemList: HashMap<Int, ItemStack> = removeItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    storageContents = cloneInventoryContents
    return false
}

fun Inventory.giveItems(items: Array<ItemStack>): Boolean {

    val cloneInventoryContents = storageContents.clone()

    val returnedItemList: HashMap<Int, ItemStack> = addItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    storageContents = cloneInventoryContents
    return false
}