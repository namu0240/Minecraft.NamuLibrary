package com.namu.namulibrary.dsl

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

inline fun item(
        material: Material,
        amount: Int = 1,
        block: ItemStack.() -> Unit
): ItemStack {
    return ItemStack(material, amount).meta(block)
}

inline fun <reified T : ItemMeta> ItemStack.meta(
        block: T.() -> Unit
): ItemStack {
    return apply {
        itemMeta = (itemMeta as? T)?.apply(block) ?: itemMeta
    }
}

inline val Material.isTool: Boolean get() = isPickAxe || isAxe || isSpade || isHoe
inline val Material.isPickAxe: Boolean get() = name.endsWith("PICKAXE")
inline val Material.isSword: Boolean get() = name.endsWith("SWORD")
inline val Material.isAxe: Boolean get() = name.endsWith("_AXE")
inline val Material.isSpade: Boolean get() = name.endsWith("SPADE")
inline val Material.isHoe: Boolean get() = name.endsWith("HOE")
inline val Material.isOre: Boolean get() = name.endsWith("ORE")
inline val Material.isIngot: Boolean get() = name.endsWith("INGOT")
inline val Material.isDoor: Boolean get() = name.endsWith("DOOR")
inline val Material.isMinecart: Boolean get() = name.endsWith("MINECART")
inline val Material.isWater: Boolean get() = this == Material.WATER
inline val Material.isLava: Boolean get() = this == Material.LAVA