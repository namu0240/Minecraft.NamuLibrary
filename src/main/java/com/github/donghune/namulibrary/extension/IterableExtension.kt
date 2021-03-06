package com.github.donghune.namulibrary.extension

fun <T> Iterable<T>.debug(debug: (T) -> Unit): Iterable<T> {
    forEach(debug)
    return this
}