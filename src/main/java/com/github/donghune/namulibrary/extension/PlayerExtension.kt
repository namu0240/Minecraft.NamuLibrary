package com.github.donghune.namulibrary.extension

import org.bukkit.entity.Player

fun Player.sendColorCodeMessage(message : String) {
    sendMessage(message.replaceChatColorCode())
}

fun Player.sendErrorMessage(message : Any) {
    sendColorCodeMessage("&c[ERROR] &f$message")
}

fun Player.sendDebugMessage(message : Any) {
    sendColorCodeMessage("&e[Debug] &f$message")
}

fun Player.sendInfoMessage(message : Any) {
    sendColorCodeMessage("&9[INFO] &f$message")
}