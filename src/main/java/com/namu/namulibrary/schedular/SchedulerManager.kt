package com.namu.namulibrary.schedular

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

fun SchedulerManager(block: SchedulerManager.() -> Unit): SchedulerManager {
    return object : SchedulerManager() {
        init {
            block.invoke(this)
        }
    }
}

abstract class SchedulerManager {

    companion object {
        private lateinit var plugin: JavaPlugin

        fun initializeSchedulerManager(plugin: JavaPlugin) {
            this.plugin = plugin
        }
    }

    var currentCycle = 0

    private var started: () -> Unit = {}
    private var doing: (Int) -> Unit = {}
    private var finished: () -> Unit = {}

    fun started(block: () -> Unit) {
        this.started = block
    }

    fun doing(block: (Int) -> Unit) {
        this.doing = block
    }

    fun finished(block: () -> Unit) {
        this.finished = block
        this.currentCycle = 0
    }

    lateinit var doingBukkitTask: BukkitTask
    lateinit var cancelBukkitTask: BukkitTask

    fun stopScheduler() {
        doingBukkitTask.cancel()
        cancelBukkitTask.cancel()
        finished()
    }

    var period : Long = 0
    var cycle : Int = 0

    fun runSecond(period: Long, cycle: Int) {
        this.period = period
        this.cycle = cycle
        started()
        doingBukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            doing(currentCycle)
            currentCycle++
        }, 0L, period * 20)
        cancelBukkitTask = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            doingBukkitTask.cancel()
            finished()
        }, period * cycle * 20)
    }

    fun runTick(period: Long, cycle: Int) {
        this.period = period
        this.cycle = cycle
        started()
        doingBukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            doing(currentCycle)
            currentCycle++
        }, 0L, period)
        cancelBukkitTask = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            doingBukkitTask.cancel()
            finished()
        }, period * cycle)
    }

}