package com.namu.namulibrary.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

abstract class GUI(
    private val plugin: JavaPlugin,
    private val size: Int,
    private val title: String
) : Listener {

    private val inventory by lazy { Bukkit.createInventory(null, size, title) }
    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()

    init {
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
    }

    abstract fun setContent()
    abstract fun onInventoryClose()
    abstract fun onInventoryOpen()

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen()
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.inventory != inventory) {
            return
        }

        clickEvents[event.rawSlot]?.invoke(event)
    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryClose()
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    fun open(player: Player) {
        player.openInventory(inventory)
        setContent()
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: (event: InventoryClickEvent) -> Unit) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}