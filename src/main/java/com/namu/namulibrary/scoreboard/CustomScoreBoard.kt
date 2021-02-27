package com.namu.namulibrary.scoreboard

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

class CustomScoreBoard {

    private lateinit var scoreboard: Scoreboard

    fun create(objectiveName: String, values: List<String>, displaySlot: DisplaySlot = DisplaySlot.SIDEBAR) {
        scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
        val objective = scoreboard.registerNewObjective(objectiveName, "Dummy")
        objective.displaySlot = displaySlot
        values.forEachIndexed { index, value ->
            val score = objective.getScore(if (value == "") getEmptyLine(index) else value)
            score.score = values.size - index
        }
    }

    fun visibleScoreboard(player: Player) {
        player.scoreboard = scoreboard
    }

    private fun getEmptyLine(index: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until index) {
            stringBuilder.append("§f")
        }
        return stringBuilder.toString()
    }
}