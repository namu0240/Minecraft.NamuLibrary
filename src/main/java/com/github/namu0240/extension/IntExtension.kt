package com.github.namu0240.extension

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,##0")

fun Int.toMoneyFormat(): String {
    return decimalFormat.format(this)
}

fun Long.toMoneyFormat(): String {
    return decimalFormat.format(this)
}