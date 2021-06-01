package com.github.namu0240.model

import com.github.namu0240.model.yaml.YamlDataSource
import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.io.File

abstract class EntityRepository<T : ConfigurationSerializable> : DataRepository<T> {

    abstract val file: File
    abstract val dataType: Class<T>

    protected val dataSource: YamlDataSource<T> by lazy { YamlDataSource(file, dataType) }

    override fun createDefaultData(key: String): T = dataSource.add(key, getDefaultData(key))
    override fun get(key: String): T? = dataSource.get(key)
    override fun getSafety(key: String): T = dataSource.get(key) ?: createDefaultData(key)
    override fun getList(): List<T> = dataSource.getList()
    override fun create(key: String, data: T): T = dataSource.add(key, data)
    override fun remove(key: String): Unit = dataSource.remove(key)
    override fun update(key: String, data: T): T = dataSource.set(key, data)
    override fun reload() = dataSource.reload()
    override fun save(key: String): Unit = dataSource.save(key)
}