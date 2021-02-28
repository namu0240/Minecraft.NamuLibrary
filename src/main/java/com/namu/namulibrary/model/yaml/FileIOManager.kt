package com.namu.namulibrary.model.yaml

import com.namu.namulibrary.extension.getYmlFileList
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.io.File
import java.util.*

abstract class FileIOManager<T : ConfigurationSerializable>(
        private val dataFolder: File,
        private val classType: Class<T>
) {

    private var objectByName: MutableMap<String, T> = mutableMapOf()

    init {
        loadFiles()
    }

    protected fun loadFiles() {
        objectByName.clear()
        val valueByKey = TreeMap<String, T>(String.CASE_INSENSITIVE_ORDER)
        dataFolder.getYmlFileList().forEach { file ->
            val key = file.name.substring(0, file.name.length - 4)
            try {
                val config = YamlConfiguration.loadConfiguration(file)
                val value = config.getSerializable("data", classType)
                valueByKey[key] = value!!
            } catch (exception: Exception) {
                println("Failed to load from $key cause : ")
                exception.printStackTrace()
            }
        }
        this.objectByName = valueByKey
    }

    protected fun getValue(key: String): T? {
        return objectByName[key]
    }

    protected fun getValueList(): List<T> {
        return objectByName.values.toList()
    }

    protected fun getKeys(): Set<String> {
        return objectByName.keys.toSet()
    }

    protected fun setValue(key: String, value: T) {
        objectByName[key] = value
    }

    protected fun removeValue(key: String) {
        objectByName.remove(key)
    }

    protected fun saveFile(key: String) {
        Thread {
            val file = File(dataFolder, "${key}.yml")
            val configuration = YamlConfiguration.loadConfiguration(file)
            configuration.set("data", getValue(key))
            configuration.save(file)
        }.start()
    }

    protected fun deleteFile(key: String) {
        File(dataFolder, "$key.yml").delete()
    }

}