package com.github.namu0240.schedular

fun SchedulerManager(block: SchedulerManager.() -> Unit): SchedulerManager {
    return object : SchedulerManager() {
        init {
            block.invoke(this)
        }
    }
}
