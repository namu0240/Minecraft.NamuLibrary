package com.namu.namulibrary.particle

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.material.MaterialData

class ParticleManager {

    var particle: Particle? = null
    var offsetX: Float = 0.toFloat()
    var offsetY: Float = 0.toFloat()
    var offsetZ: Float = 0.toFloat()
    var speed: Float = 0.toFloat()
    var amount: Int = 0
    var data: Any? = null

    fun spawn(center: Location) {
        center.world!!.spawnParticle(particle!!, center, amount, offsetX.toDouble(), offsetY.toDouble(), offsetZ.toDouble(), speed.toDouble(), data)
    }

    constructor(particle: Particle) {
        initData(particle, 0f, 0f, 0f, 0f, 1, null, null, 0.toByte())
    }

    constructor(particle: Particle, color: Color) {
        initData(particle, 0f, 0f, 0f, 0f, 1, color, null, 0.toByte())
    }

    constructor(particle: Particle, speed: Float, amount: Int) {
        initData(particle, 0f, 0f, 0f, speed, amount, null, null, 0.toByte())
    }

    constructor(particle: Particle, color: Color, speed: Float, amount: Int) {
        initData(particle, 0f, 0f, 0f, speed, amount, color, null, 0.toByte())
    }

    constructor(particle: Particle, speed: Float, amount: Int, material: Material, materialData: Byte) {
        initData(particle, 0f, 0f, 0f, speed, amount, null, material, materialData)
    }

    fun initData(particle: Particle, offsetX: Float, offsetY: Float, offsetZ: Float, speed: Float, amount: Int,
                 color: Color?, material: Material?, materialData: Byte) {

        this.particle = particle
        this.offsetX = offsetX
        this.offsetY = offsetY
        this.offsetZ = offsetZ
        this.speed = speed
        this.amount = amount

        if (color != null && (particle == Particle.REDSTONE || particle == Particle.SPELL_MOB
                        || particle == Particle.SPELL_MOB_AMBIENT)) {
            displayLegacyColored(speed, color)
        } else if (particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST
                || particle.name == "FALLING_DUST") {
            if (material == null || material == Material.AIR) {
                return
            }
            data = MaterialData(material, materialData)
        }

    }

    protected fun displayLegacyColored(speed: Float, color: Color) {

        amount = 0
        if (speed == 0f) {
            this.speed = 1f
        }
        offsetX = color.red.toFloat() / 255
        offsetY = color.green.toFloat() / 255
        offsetZ = color.blue.toFloat() / 255

        if (offsetX < java.lang.Float.MIN_NORMAL) {
            offsetX = java.lang.Float.MIN_NORMAL
        }

    }

}
