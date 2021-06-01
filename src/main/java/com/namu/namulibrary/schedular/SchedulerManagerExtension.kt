package com.namu.namulibrary.schedular

fun SchedulerManager(block: SchedulerManager.() -> Unit): SchedulerManager {
    return object : SchedulerManager() {
        init {
            block.invoke(this)
        }
    }
}
