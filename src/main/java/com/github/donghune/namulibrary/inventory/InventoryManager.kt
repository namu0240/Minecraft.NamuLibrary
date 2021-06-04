package com.github.donghune.namulibrary.inventory

import com.github.shynixn.mccoroutine.launch
import com.github.shynixn.mccoroutine.minecraftDispatcher
import com.github.shynixn.mccoroutine.registerSuspendingEvents
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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
        private val title: String,
) : Listener {

    protected val inventory by lazy { Bukkit.createInventory(null, size, title) }
    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()

    abstract suspend fun setContent()
    abstract suspend fun onInventoryClose(event: InventoryCloseEvent)
    abstract suspend fun onInventoryOpen(event: InventoryOpenEvent)

    @EventHandler
    suspend fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen(event)
    }

    @EventHandler
    suspend fun onInventoryClickEvent(event: InventoryClickEvent) {
        coroutineScope {
            if (event.inventory != inventory) {
                return@coroutineScope
            }

            clickEvents[event.rawSlot]?.invoke(event)
        }
    }

    @EventHandler
    suspend fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    fun open(player: Player) {
        plugin.launch(plugin.minecraftDispatcher) {
            Bukkit.getPluginManager().registerSuspendingEvents(this@GUI, plugin)
            player.openInventory(inventory)
            setContent()
        }
    }

    fun openLater(player: Player) {
        plugin.launch(plugin.minecraftDispatcher) {
            delay(50)
            open(player)
        }
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: (event: InventoryClickEvent) -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}