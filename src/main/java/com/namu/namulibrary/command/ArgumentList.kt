package com.namu.namulibrary.command

import java.util.NoSuchElementException

class ArgumentList(private val arguments: Array<String>, private var cursor: Int) {

    val isLast: Boolean
        get() = remain() == 1

    val isEmpty: Boolean
        get() = this.arguments.isEmpty()

    fun getCursor(): Int {
        return this.cursor
    }

    fun setCursor(cursor: Int): ArgumentList {
        if (cursor < 0 || cursor > this.arguments.size) {
            throw ArrayIndexOutOfBoundsException(cursor)
        }
        this.cursor = cursor

        return this
    }

    fun remain(): Int {
        return this.arguments.size - this.cursor
    }

    operator fun hasNext(): Boolean {
        return this.cursor < this.arguments.size
    }

    operator fun next(): String {
        if (this.cursor >= this.arguments.size) {
            throw NoSuchElementException()
        }
        return this.arguments[this.cursor++]
    }

    fun nextBoolean(): Boolean {
        return java.lang.Boolean.parseBoolean(next())
    }

    @JvmOverloads
    fun nextByte(defaultValue: Byte = 0.toByte()): Byte {
        try {
            return java.lang.Byte.parseByte(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    @JvmOverloads
    fun nextShort(defaultValue: Short = 0.toShort()): Short {
        try {
            return java.lang.Short.parseShort(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    @JvmOverloads
    fun nextInt(defaultValue: Int = 0): Int {
        try {
            return Integer.parseInt(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    fun nextLong(defaultValue: Long = 0L): Long {
        try {
            return java.lang.Long.parseLong(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    fun nextFloat(defaultValue: Float = 0.0f): Float {
        try {
            return java.lang.Float.parseFloat(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    fun nextDouble(defaultValue: Double = 0.0): Double {
        try {
            return java.lang.Double.parseDouble(next())
        } catch (e: NumberFormatException) {
        }

        return defaultValue
    }

    fun peek(): String {
        return this.arguments[this.cursor]
    }

    private fun checkJoinLength(length: Int) {
        if (length < 0 || length > remain()) {
            throw ArrayIndexOutOfBoundsException()
        }
    }

    fun join(length: Int = remain()): String {
        checkJoinLength(length)

        val args = this.arguments
        val index = this.cursor
        var capacity = 0
        for (i in 0 until length) {
            capacity += args[index + i].length
        }
        val builder = StringBuilder(capacity)
        for (i in 0 until length) {
            builder.append(args[index + i])
        }
        this.cursor += length

        return builder.toString()
    }

    @JvmOverloads
    fun join(separator: Char, length: Int = remain()): String {
        checkJoinLength(length)

        val args = this.arguments
        val index = this.cursor
        var capacity = Math.max(length - 1, 1)
        for (i in 0 until length) {
            capacity += args[index + i].length
        }
        val builder = StringBuilder(capacity)
        for (i in 0 until length) {
            builder.append(args[index + i])
            if (i + 1 < length) {
                builder.append(separator)
            }
        }
        this.cursor += length

        return builder.toString()
    }

    fun join(separator: String, length: Int = remain()): String {
        checkJoinLength(length)

        val args = this.arguments
        val index = this.cursor
        var capacity = Math.max(length - 1, 1) * separator.length
        for (i in 0 until length) {
            capacity += args[index + i].length
        }
        val builder = StringBuilder(capacity)
        for (i in 0 until length) {
            builder.append(args[index + i])
            if (i + 1 < length) {
                builder.append(separator)
            }
        }
        this.cursor += length

        return builder.toString()
    }
}
