package com.namu.namulibrary.command

object Message {

    const val HELP_DESCRIPTION = "명령의 도움말을 확인합니다."
    const val NOT_EXISTS_PERFORMABLE_COMMAND = "사용 가능한 명령이 없습니다."
    const val NO_PERMISSION = "당신은 이 명령을 실행할 권한이 없습니다."
    const val NO_COMPONENT = "알 수 없는 명령입니다. 혹시 이 명령을 찾으세요?"
    const val CANNOT_PERFORM_IN_CONSOLE = "콘솔에서 사용 할 수 없는 명령입니다."
    const val CANNOT_FIND_PLAYER = "플레이어를 찾을 수 없습니다."

    fun createErrorMessage(label: String, componentLabel: String, t: Throwable, className: String): String {
        val err = StringBuilder(64).append('/').append(label).append(" §6").append(componentLabel)
                .append(" §r명령을 실행하는 도중 오류가 발생했습니다. \n§c").append(t.javaClass.name).append(": ")
                .append(t.localizedMessage).append("§7")

        val elements = t.stackTrace

        for (element in elements) {
            if (element.className == className)
                break

            err.append("\nat ").append(element)
        }

        return err.toString()
    }
}