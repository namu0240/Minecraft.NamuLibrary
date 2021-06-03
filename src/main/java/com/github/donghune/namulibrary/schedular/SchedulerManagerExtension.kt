package com.github.donghune.namulibrary.schedular

fun SchedulerManager(block: SchedulerManager.() -> Unit): SchedulerManager {
    return object : SchedulerManager() {
        init {
            block.invoke(this)
        }
    }
}
