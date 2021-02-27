package com.namu.namulibrary.extension

import com.namu.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// 팩토리 패턴으로 아이템 스택 생성 및 수정하기


class ItemBuilder {

    private var itemStack = ItemStack(Material.AIR)

    fun setDisplay(displayName: String): ItemBuilder {
        itemStack.itemMeta = itemStack.itemMeta?.apply {
            setDisplayName(displayName)
        }
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        itemStack.itemMeta = itemStack.itemMeta?.apply {
            setLore(lore);
        }
        return this
    }

    fun setMaterial(material: Material): ItemBuilder {
        itemStack.type = material
        return this
    }

    fun setNBTTagComponent(data: Any): ItemBuilder {
        itemStack.apply {
            addNBTTagCompound(data)
        }
        return this
    }

    fun build(): ItemStack {
        return itemStack
    }

}