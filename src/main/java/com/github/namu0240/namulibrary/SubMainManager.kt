package com.github.namu0240.namulibrary

import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

abstract class SubMainManager(
        val dataFolder: File,
        private val plugin: JavaPlugin
) {

    open fun onEnable() {
        setupCommands()
        setupListeners()
        setupSchedulers()
        setupRegisterClass()
    }

    abstract fun onDisable()

    protected abstract fun setupCommands()

    protected abstract fun setupListeners()

    protected abstract fun setupRegisterClass()

    protected abstract fun setupSchedulers()

    fun registerEvent(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    fun registerClass(classType: Class<out ConfigurationSerializable>) {
        ConfigurationSerialization.registerClass(classType, classType.simpleName)
    }

}