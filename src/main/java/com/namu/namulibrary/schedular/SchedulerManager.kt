package com.namu.namulibrary.schedular

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

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

    lateinit var aBukkitTask : BukkitTask
    lateinit var bBukkitTask : BukkitTask

    fun stopScheduler() {
        aBukkitTask.cancel()
        bBukkitTask.cancel()
        finished()
    }

    fun runSecond(period: Long, cycle: Int) {
        started()
        aBukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable { doing() }, 0L, period * 20)
        bBukkitTask = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            aBukkitTask.cancel()
            finished()
        }, period * cycle * 20)
    }

    fun runTick(period: Long, cycle: Int) {
        started()
        aBukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable { doing() }, 0L, period * 20)
        bBukkitTask = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            aBukkitTask.cancel()
            finished()
        }, period * cycle)
    }

}