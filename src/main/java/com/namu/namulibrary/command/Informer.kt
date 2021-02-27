package com.namu.namulibrary.command

interface Informer<T> {
    fun information(index: Int, builder: StringBuilder, o: T)
}