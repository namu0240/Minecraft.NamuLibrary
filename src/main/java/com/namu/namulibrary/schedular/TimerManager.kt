package com.namu.namulibrary.schedular

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

/**
 * 타이머 클래스
 *
 * @author HITC
 */
abstract class TimerManager {

    private var pl: Plugin? = null // plugin 변수
    // 몇초가 지났는지 가져오는 변수
    var count: Int = 0 // 몇번 돌았는지
    var maxCount: Int = 0 // 몇번 돌 것인지
    /**
     * 타이머 순환 확인
     * @return 타이머가 작동중이면 true 아닐 경우 false
     */
    var isRunning = false // 타이머가 도는지 체크하는 변수
    var isInfinite = false // 타이머가 무한 타이머인지 체크하는 변수
    var reverseCount: Int = 0 // 반전횟수
    // 몇틱마다 타이머가 실행 될 것인지 설정하는 메소드
    var delayTick = 20 // 틱
    var startDelay = 20

    // private BukkitTask taskID; // 해당 타이머의 ID ( 취소할 때 사용 )
    var taskID: BukkitTask? = null // 해당 타이머의 ID ( 취소할 때 사용 )

    // 타이머가 시작될때 이벤트
    abstract fun startEventTimer()

    // 타이머가 돌고 있을때 이벤트
    abstract fun runningEventTimer(count: Int)

    // 타이머가 끝났을때 이벤트
    abstract fun endEventTimer()

    // 타이머가 끝났을때 최종적인 이벤트
    abstract fun finalEventEndTimer()

    // 플러그인을 등록시키는 메소드
    fun setPlugin(pl: Plugin): TimerManager {
        this.pl = pl
        return this
    }

    // 플러그인을 가져오는 메소드
    fun getPlugin(): Plugin? {
        return pl
    }

    // 타이머를 시작하는 메소드 ( 변수가 없을 때 )
    fun StartTimer() {
        taskID = Bukkit.getScheduler().runTaskTimer(pl!!, TS(), startDelay.toLong(), delayTick.toLong())
    }

    // 타이머를 시작하는 메소드 ( 변수가3개 일때 )
    @JvmOverloads
    fun startTimer(maxCount: Int, ReverseCount: Int = 0, startDelay: Int = 0, delayTick: Int = 20) {

        // 만약 동작중이라면 취소
        if (isRunning)
            return

        // 만약 플러그인이 null 라면 취소
        if (pl == null)
            return

        // 값설정
        this.startDelay = startDelay
        this.delayTick = delayTick
        isRunning = true
        this.maxCount = maxCount // 20틱 = 1초
        count = maxCount
        isInfinite = ReverseCount <= -1 // ?
        this.reverseCount = ReverseCount
        taskID = Bukkit.getScheduler().runTaskTimer(pl!!, TS(), startDelay.toLong(), delayTick.toLong())
        startEventTimer()
    }

    // 타이머가 끝났을때
    fun EndTimer() {
        StopTimer()
        endEventTimer()
    }

    // 변수처리 및 최종 이벤트 실행
    fun StopTimer() {
        // taskID.cancel();
        taskID!!.cancel()
        count = 0
        maxCount = 0
        reverseCount = 0
        isRunning = false
        isInfinite = false
        finalEventEndTimer()
    }

    private inner class TS : Runnable {
        override fun run() {
            runningEventTimer(count)
            if (!isInfinite) {
                /*
				 * 무한이 아닐경우
				 */
                if (reverseCount > 0) {
                    if (count <= 0) {
                        count = maxCount
                        reverseCount -= 1
                        return
                    }
                    --count
                } else {
                    if (count <= 0) {
                        EndTimer()
                        return
                    }
                    --count
                }
            } else {
                /*
				 * 무한일 경우 만약 카운트가 0일경우 타이머끝이벤트를 실행시킨다.
				 */
                if (count <= 0) {
                    count = maxCount
                    endEventTimer()
                    return
                }
                --count
            }
        }
    }

}// 타이머를 시작하는 메소드 ( 변수가 1개 일때 )