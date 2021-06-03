package com.github.donghune.namulibrary.extension

import com.github.donghune.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

// 팩토리 패턴으로 아이템 스택 생성 및 수정하기

class ItemBuilder {

    private var itemStack = ItemStack(Material.AIR)

    fun setDisplay(displayName: String): ItemBuilder {
        itemStack.itemMeta = itemStack.itemMeta?.apply {
            setDisplayName(displayName.replaceChatColorCode())
        }
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        itemStack.itemMeta = itemStack.itemMeta?.apply {
            setLore(lore.map { it.replaceChatColorCode() })
        }
        return this
    }

    fun setAmount(amount : Int) : ItemBuilder {
        itemStack.amount = amount
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

    fun setCustomModelData(customModelData: Int): ItemBuilder {
        itemStack.itemMeta = itemStack.itemMeta?.apply {
            this.setCustomModelData(customModelData)
        }
        return this
    }

    fun build(): ItemStack {
        return itemStack
    }

}