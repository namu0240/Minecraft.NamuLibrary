package com.namu.namulibrary.command

object HelpUtils {
    fun createHelp(label: String, componentLabel: String): String {
        return createHelp(StringBuilder(), label, componentLabel).toString()
    }

    fun createHelp(label: String, componentLabel: String, usage: String): String {
        return createHelp(StringBuilder(), label, componentLabel, usage).toString()
    }

    fun createHelp(label: String, componentLabel: String, usage: String, description: String): String {
        return createHelp(StringBuilder(), label, componentLabel, usage, description).toString()
    }

    fun createHelp(builder: StringBuilder, label: String, componentLabel: String): StringBuilder {
        return builder.append("§r/").append(label).append(" §6").append(componentLabel)
    }

    fun createHelp(builder: StringBuilder, label: String, componentLabel: String, usage: String?): StringBuilder {
        createHelp(builder, label, componentLabel)

        if (usage != null)
            builder.append(" §r").append(usage)

        return builder
    }

    fun createHelp(builder: StringBuilder, label: String, componentLabel: String, usage: String,
                   description: String?): StringBuilder {
        createHelp(builder, label, componentLabel, usage)

        if (description != null)
            builder.append(" §r").append(description)

        return builder
    }
}
