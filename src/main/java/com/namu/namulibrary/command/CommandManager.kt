package com.namu.namulibrary.command

import java.lang.ref.SoftReference
import java.util.ArrayList
import java.util.LinkedHashMap
import kotlin.collections.Map.Entry

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class CommandManager(private val op: Boolean) {

    internal val components = LinkedHashMap<String, CommandComponent>()

    private var executor: CommandExecutor? = null

    private inner class CommandHelp : CommandComponent(), Informer<Entry<String, CommandComponent>> {

        private var label: String? = null
        override val usage: String = "<page>"
        override val description: String = Message.HELP_DESCRIPTION
        override val argumentsLength: Int = 0

        override fun onCommand(sender: CommandSender, label: String, command: Command, componentLabel: String,
                               args: ArgumentList): Boolean {
            val componentList = this@CommandManager.getPerformableList(sender)

            if (op && !sender.isOp) {
                return true
            }

            if (componentList.isEmpty()) {
                sender.sendMessage(Message.NOT_EXISTS_PERFORMABLE_COMMAND)
                return true
            }

            this.label = label

            for (message in Information.pageInformation(label, componentList, this,
                    if (args.hasNext()) Math.max(args.nextInt() - 1, 0) else 0, 9))
                sender.sendMessage(message)

            this.label = null

            return true
        }

        override fun information(index: Int, builder: StringBuilder, o: Entry<String, CommandComponent>) {
            val component = o.value
            label?.let { HelpUtils.createHelp(builder, it, o.key, component.usage, component.description) }
        }
    }

    fun addHelp(label: String): CommandManager {
        addComponent(label, CommandHelp())

        return this
    }

    fun addComponent(label: String?, component: CommandComponent?): CommandManager {
        var label: String? = label ?: throw NullPointerException("Label cannot be null")

        if (component == null)
            throw NullPointerException("Component can not be null")

        label = label!!.toLowerCase()

        if (this.components.containsKey(label))
            throw IllegalArgumentException("'$label\' is already registered command")

        this.components[label] = component
        return this
    }

    fun getPerformableList(sender: CommandSender): ArrayList<Entry<String, CommandComponent>> {
        val temp = temp()

        for (component in this.components.entries) {
            if (component.value.testPermission(sender))
                temp.add(component)
        }

        return temp
    }

    fun getComponent(label: String): CommandComponent? {
        return components[label.toLowerCase()]
    }

    fun getNearestLabel(sender: CommandSender, label: String): String? {
        var nearest: String? = null
        var distance = Integer.MAX_VALUE

        for ((componentLabel) in getPerformableList(sender)) {

            val curDistance = getLevenshteinDistance(label, componentLabel)

            if (curDistance < distance) {
                distance = curDistance
                nearest = componentLabel

                if (distance == 0)
                    break
            }
        }

        return nearest
    }

    fun onCommand(sender: CommandSender, command: Command, label: String): Boolean {
        return false
    }

    fun register(command: PluginCommand?): CommandManager {
        if (command == null)
            throw NullPointerException("Unregistered command: " + command!!)

        if (this.executor == null)
            this.executor = CommandExecutor()

        command.setExecutor(this.executor)
        command.tabCompleter = this.executor

        if (command.permission != null)
            command.permissionMessage = Message.NO_PERMISSION

        return this
    }

    private inner class CommandExecutor : TabExecutor {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
            val performables = getPerformableList(sender)

            if (performables.isEmpty()) {
                sender.sendMessage('/'.toString() + label + ' '.toString() + Message.NOT_EXISTS_PERFORMABLE_COMMAND)
                return true
            }

            if (args.size == 0) {
                if (this@CommandManager.onCommand(sender, command, label))
                    return true

                var usage: StringBuilder

                for ((key, value) in performables) {
                    usage = StringBuilder().append('/').append(label).append("§6")
                    usage.append(' ').append(key).append(' ').append(value.usage).append(" - ").append(value.description)
                    sender.sendMessage(usage.toString())
                }

                return true

            }

            val componentLabel = args[0]

            val component = getComponent(componentLabel)

            if (component == null) {
                sender.sendMessage('/'.toString() + label + " §6" + componentLabel + ' '.toString() + Message.NO_COMPONENT)
                sender.sendMessage('/'.toString() + label + " §6" + getNearestLabel(sender, componentLabel))
                return true
            }

            val permissionMessage = component.getPermissionMessage(sender)

            if (permissionMessage != null) {
                sender.sendMessage("/$label §6$componentLabel §r$permissionMessage")
                return true
            }

            try {
                if (args.size <= component.argumentsLength || !component.onCommand(sender, label, command, componentLabel, ArgumentList(args, 1)))
                    sender.sendMessage(
                            HelpUtils.createHelp(label, componentLabel, component.usage, component.description))
            } catch (t: Throwable) {
                t.printStackTrace()

                if (sender is Player) {
                    sender.sendMessage(
                            Message.createErrorMessage(label, componentLabel, t, CommandExecutor::class.java.name))
                }
            }

            return true
        }

        override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {
            val componentLabel = args[0]

            if (args.size == 1) {
                val len = componentLabel.length
                val list = ArrayList<String>()

                for (name in components.keys) {
                    if (name.regionMatches(0, componentLabel, 0, len, ignoreCase = true))
                        list.add(name)
                }

                return list
            }

            val component = getComponent(componentLabel)

            if (component != null && component.testPermission(sender)) {
                val iterable = component.onTabComplete(sender, command, label, componentLabel,
                        ArgumentList(args, 1)) ?: return null

                if (iterable is List<*>)
                    return iterable as List<String>

                val list = ArrayList<String>()

                for (name in iterable)
                    list.add(name)

                return list
            }

            return emptyList()
        }
    }

    companion object {

        private var tempReference: SoftReference<ArrayList<Entry<String, CommandComponent>>>? = null

        private fun temp(): ArrayList<Entry<String, CommandComponent>> {
            var temp: ArrayList<Entry<String, CommandComponent>>?

            if (tempReference == null) {
                temp = ArrayList()
                tempReference = SoftReference(temp)
            } else {
                temp = tempReference!!.get()

                if (temp == null) {
                    temp = ArrayList()
                    tempReference = SoftReference(temp)
                } else
                    temp.clear()
            }

            return temp
        }

        private fun getLevenshteinDistance(a: String, b: String): Int {
            val len0 = a.length + 1
            val len1 = b.length + 1

            var cost = IntArray(len0)
            var newcost = IntArray(len0)

            for (i in 0 until len0)
                cost[i] = i

            for (j in 1 until len1) {
                newcost[0] = j

                for (i in 1 until len0) {
                    val match = if (a[i - 1] == b[j - 1]) 0 else 1

                    val cost_replace = cost[i - 1] + match
                    val cost_insert = cost[i] + 1
                    val cost_delete = newcost[i - 1] + 1

                    newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace)
                }

                val swap = cost
                cost = newcost
                newcost = swap
            }

            return cost[len0 - 1]
        }
    }
}