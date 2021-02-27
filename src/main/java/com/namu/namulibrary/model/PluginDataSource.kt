package com.namu.namulibrary.model

interface PluginDataSource<T> {
    fun get(key: String): T?
    fun getList() : List<T>
    fun set(key: String, data: T) : T
    fun add(key: String, data: T) : T
    fun remove(key: String)
    fun save(key : String)
    fun reload()
}