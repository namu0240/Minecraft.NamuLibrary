package com.namu.namulibrary.extension

import org.bukkit.command.CommandSender

fun CommandSender.sendColorCodeMessage(message: String) {
    sendMessage(message.replaceChatColorCode())
}

fun CommandSender.sendErrorMessage(message: Any) {
    sendColorCodeMessage("&c[ERROR] &f$message")
}

fun CommandSender.sendDebugMessage(message: Any) {
    sendColorCodeMessage("&e[Debug] &f$message")
}

fun CommandSender.sendInfoMessage(message: Any) {
    sendColorCodeMessage("&9[INFO] &f$message")
}