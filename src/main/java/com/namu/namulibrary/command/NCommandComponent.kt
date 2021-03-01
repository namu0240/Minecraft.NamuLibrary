package com.namu.namulibrary.command

import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

interface NCommandComponent {
    fun getCommand() : CommandManager

    fun command(command: String, isOpCommand: Boolean, block: CommandManager.() -> Unit): CommandManager {
        return CommandManager(isOpCommand).apply {
            block.invoke(this)
        }
    }

    fun CommandManager.self(
        command: String,
        usage: String,
        description: String,
        block: (Player, ArgumentList) -> Boolean
    ) {
        addComponent(command, object : SelfCommandComponent() {
            override val usage: String = usage
            override val description: String = description
            override val argumentsLength: Int = 0
            override fun onCommand(
                sender: CommandSender,
                label: String,
                command: Command,
                componentLabel: String,
                args: ArgumentList
            ): Boolean {
                return block.invoke(sender as Player, args)
            }
        })
    }

    fun CommandManager.target(
        command: String,
        usage: String,
        description: String,
        block: (Player, OfflinePlayer?, ArgumentList) -> Boolean
    ) {
        addComponent(command, object : TargetCommandComponent() {
            override val usage: String = usage
            override val description: String = description
            override val argumentsLength: Int = 0
            override fun onCommand(
                sender: Player,
                target: OfflinePlayer,
                label: String,
                command: Command,
                componentLabel: String,
                args: ArgumentList
            ): Boolean {
                return block.invoke(sender, target, args)
            }
        })
    }
}