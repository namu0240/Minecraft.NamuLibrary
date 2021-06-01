package com.github.namu0240.namulibrary.model

interface DataRepository<T> {
    fun getDefaultData(key: String): T
    fun createDefaultData(key: String): T
    fun get(key: String): T?
    fun getSafety(key: String): T
    fun getList(): List<T>
    fun create(key: String, data: T): T
    fun remove(key: String): Unit
    fun update(key: String, data: T): T
    fun reload()
    fun save(key: String): Unit
}


