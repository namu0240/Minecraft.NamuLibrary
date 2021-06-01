package com.github.namu0240.namulibrary.inventory

import com.github.namu0240.namulibrary.NamuLibrary
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

    protected val inventory by lazy { Bukkit.createInventory(null, size, title) }
    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()

    init {
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
    }

    abstract fun setContent()
    abstract fun onInventoryClose(event: InventoryCloseEvent)
    abstract fun onInventoryOpen(event: InventoryOpenEvent)

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen(event)
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

        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    fun open(player: Player) {
        player.openInventory(inventory)
        setContent()
    }

    fun openLater(player: Player) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            open(player)
        }, 1L)
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: (event: InventoryClickEvent) -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}