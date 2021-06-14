package com.github.donghune.namulibrary.extension
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

fun Inventory.isContentFull(): Boolean {
    return contents.count { itemStack -> itemStack != null && itemStack.type == Material.AIR } == size
}

fun Inventory.isContentEmpty(): Boolean {
    return !isContentFull()
}

fun Inventory.hasItems(requireItems: Array<ItemStack>): Boolean {
    val requireMap = requireItems
            .filter { itemStack: ItemStack -> itemStack != null && itemStack.type != Material.AIR }
            .groupBy { itemStack: ItemStack -> itemStack.itemMeta?.displayName ?: itemStack.type }
            .mapValues { data -> data.value.sumBy { itemStack: ItemStack -> itemStack.amount } }
            .toMap()

    val playerMap = storageContents
            .filter { itemStack: ItemStack -> itemStack != null && itemStack.type != Material.AIR }
            .groupBy { itemStack: ItemStack -> itemStack.itemMeta?.displayName ?: itemStack.type }
            .mapValues { data -> data.value.sumBy { itemStack: ItemStack -> itemStack.amount } }
            .toMap()

    return requireMap
            .map { entry: Map.Entry<Any, Int> -> entry.value <= (playerMap[entry.key] ?: 0) }
            .find { isHas: Boolean -> !isHas }
            .let { isFind: Boolean? -> isFind == null }
}

fun Inventory.takeItems(items: Array<ItemStack>): Boolean {

    val returnedItemList: HashMap<Int, ItemStack> = removeItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    return false
}

fun Inventory.giveItems(items: Array<ItemStack>): Boolean {

    val returnedItemList: HashMap<Int, ItemStack> = addItem(*items) // 추가 되지 못한 아이템 목록

    if (returnedItemList.isEmpty()) {
        return true
    }

    return false
}