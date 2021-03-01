package com.namu.namulibrary

import org.bukkit.plugin.java.JavaPlugin

class NamuLibrary : JavaPlugin() {

    companion object {
        private lateinit var instance : NamuLibrary

        fun getInstance(): NamuLibrary {
            return instance
        }
    }

    override fun onEnable() {
        instance = this
    }
    override fun onDisable() {
    }
}