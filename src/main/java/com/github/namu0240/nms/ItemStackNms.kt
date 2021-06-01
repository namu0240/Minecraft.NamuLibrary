package com.github.namu0240.nms

import com.google.gson.Gson
import net.minecraft.server.v1_16_R3.NBTTagCompound
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

inline fun <reified T> ItemStack.addNBTTagCompound(data: T): ItemStack {
    return CraftItemStack.asBukkitCopy(
            CraftItemStack.asNMSCopy(this)
                    .apply {
                        tag = (tag ?: NBTTagCompound())
                                .apply {
                                    setString(T::class.simpleName, Gson().toJson(data))
                                }
                    }
    )
}

inline fun <reified T> ItemStack.getNBTTagCompound(data: Class<T>): T? {
    val jsonData = CraftItemStack.asNMSCopy(this).tag?.getString(T::class.simpleName) ?: return null
    return Gson().fromJson(jsonData, data)
}