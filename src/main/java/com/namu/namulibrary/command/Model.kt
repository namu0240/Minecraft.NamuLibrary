package com.namu.namulibrary.command

import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: JavaPlugin

fun a() {

    plugin.command("money", false) {
        self("view", "", "view the my money") { player, args ->
            true
        }

        target("give", "[money]", "give the money to player") { player, target, args ->
            true
        }
    }

}

fun JavaPlugin.command(command: String, isOpCommand: Boolean, block: CommandManager.() -> Unit) {
    CommandManager(isOpCommand).apply {
        block.invoke(this)
    }.register(getCommand(command))
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