package com.namu.namulibrary.command

import org.bukkit.Bukkit
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class CommandComponent {

    abstract val usage: String
    abstract val description: String
    open val permission: String? = null
    abstract val argumentsLength: Int

    private fun createMessage(sender: CommandSender, label: String, componentLabel: String, message: String): String {
        return "§7" + sender.name + ": §r" + label + " §6" + componentLabel + " §r" + message
    }

    protected fun broadcast(sender: CommandSender, label: String, componentLabel: String, message: String) {
        var message = message
        if (sender is BlockCommandSender) {
            val world = sender.block.world

            val value = world.getGameRuleValue("commandBlockOutput")

            if (value != null && !java.lang.Boolean.parseBoolean(value))
                return
        } else if (sender is Player) {
            val world = sender.world

            val value = world.getGameRuleValue("sendCommandFeedback")

            if (value != null && value == "false")
                return
        }

        message = createMessage(sender, label, componentLabel, message)
        val permission = this.permission

        if (permission != null) {
            Bukkit.getConsoleSender().sendMessage(message)

            for (player in Bukkit.getOnlinePlayers())
                if (player.hasPermission(permission))
                    player.sendMessage(message)
        } else
            Bukkit.broadcastMessage(message)
    }

    fun testPermission(sender: CommandSender): Boolean {
        val permission = this.permission

        return permission == null || sender.hasPermission(permission)
    }

    fun getPermissionMessage(sender: CommandSender): String? {
        val permission = this.permission

        return if (permission == null || sender.hasPermission(permission)) null else Message.NO_PERMISSION

    }

    open fun onTabComplete(sender: CommandSender, command: Command, label: String, componentLabel: String,
                           args: ArgumentList): Iterable<String> {
        return emptyList()
    }

    open fun onCommand(sender: CommandSender, label: String, command: Command, componentLabel: String,
                       args: ArgumentList): Boolean {
        return false
    }

}
