package com.namu.namulibrary.command

import com.namu.namulibrary.extension.sendErrorMessage
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class TargetCommandComponent : CommandComponent() {

    override val permission: String? = null

    override fun onCommand(sender: CommandSender, label: String, command: Command, componentLabel: String, args: ArgumentList): Boolean {
        val player = sender as Player
        val targetName = args.next()
        val targetPlayer = Bukkit.getOfflinePlayers().find { it.name == targetName }

        if (targetPlayer == null) {
            sender.sendErrorMessage(Message.CANNOT_FIND_PLAYER)
            return false
        }

        return onCommand(sender, targetPlayer, label, command, componentLabel, args)
    }

    open fun onCommand(sender: Player, target: OfflinePlayer, label: String, command: Command, componentLabel: String, args: ArgumentList): Boolean {
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