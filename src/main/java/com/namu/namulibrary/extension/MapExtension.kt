package com.namu.namulibrary.extension

fun <T> MutableMap<T, Int>.count(key: T, value: Int = 1) {
    this[key] = (this[key] ?: 0) + value
}