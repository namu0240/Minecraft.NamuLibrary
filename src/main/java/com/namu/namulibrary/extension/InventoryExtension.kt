package com.namu.namulibrary.extension

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.HashMap

fun Inventory.takeItems(items: Array<ItemStack>): Boolean {
    val cloneInventoryContents = storageContents.clone()

    val returnedItemList: HashMap<Int, ItemStack> = removeItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    storageContents = cloneInventoryContents
    return true
}

fun Inventory.giveItems(items: Array<ItemStack>): Boolean {

    val cloneInventoryContents = storageContents.clone()

    val returnedItemList: HashMap<Int, ItemStack> = addItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    storageContents = cloneInventoryContents
    return true
}