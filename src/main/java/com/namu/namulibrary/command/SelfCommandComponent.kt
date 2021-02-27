package com.namu.namulibrary.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class SelfCommandComponent : CommandComponent() {

    override fun onCommand(sender: CommandSender, label: String, command: Command, componentLabel: String, args: ArgumentList): Boolean {
        return onCommand(sender as Player, label, command, componentLabel, args)
    }

    open fun onCommand(sender: Player, label: String, command: Command, componentLabel: String, args: ArgumentList): Boolean {
        return false
    }


    override fun onTabComplete(sender: CommandSender, command: Command, label: String, componentLabel: String, args: ArgumentList): Iterable<String> {
        return if (args.getCursor() == 1) {
            Bukkit.getOfflinePlayers().filter { it.name!!.startsWith(args.peek()) }.map { it.name!! }
        } else {
            onTabComplete(label, args)
        }
    }

    open fun onTabComplete(label: String, args: ArgumentList): Iterable<String> {
        return emptyList()
    }
}