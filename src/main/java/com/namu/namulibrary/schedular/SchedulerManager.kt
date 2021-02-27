package com.namu.namulibrary.schedular

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

abstract class SchedulerManager {

    companion object {
        private lateinit var plugin: JavaPlugin

        fun initializeSchedulerManager(plugin: JavaPlugin) {
            this.plugin = plugin
        }
    }

    abstract fun started()
    abstract fun doing()
    abstract fun finished()

    fun run(period: Long, cycle: Int) {
        started()
        val bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable { doing() }, 0L, period * 20)
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            bukkitTask.cancel()
            finished()
        }, period * cycle * 20)
    }
}