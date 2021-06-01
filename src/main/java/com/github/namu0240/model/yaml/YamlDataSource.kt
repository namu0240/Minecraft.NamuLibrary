package com.github.namu0240.model.yaml

import com.github.namu0240.model.PluginDataSource
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.io.File

class YamlDataSource<T : ConfigurationSerializable>(
        dataFolder: File,
        classType: Class<T>,
        private val isAutoSave: Boolean = true
) : PluginDataSource<T>, FileIOManager<T>(dataFolder, classType) {

    override fun get(key: String): T? {
        return getValue(key)
    }

    override fun getList(): List<T> {
        return getValueList()
    }

    override fun set(key: String, data: T): T {
        setValue(key, data)
        if (isAutoSave) saveFile(key)
        return data
    }

    override fun add(key: String, data: T): T {
        setValue(key, data)
        if (isAutoSave) saveFile(key)
        return data
    }

    override fun remove(key: String) {
        removeValue(key)
        deleteFile(key)
    }

    override fun save(key: String) {
        saveFile(key)
    }

    override fun reload() {
        loadFiles()
    }

}