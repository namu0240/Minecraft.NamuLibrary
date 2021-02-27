package com.namu.namulibrary.command

object Information {

    const val LINE = "────────"
    const val DEFAULT_LINE_COLOR = "§c"
    const val DEFAULT_NAME_COLOR = "§6"

    fun pageHeader(line: String, color: String, name: String, page: Int, total: Int, size: Int): String {
        return pageHeader(StringBuilder(), line, color, name, page, total, size).toString()
    }

    fun pageHeader(builder: StringBuilder, name: String, page: Int, total: Int, size: Int): StringBuilder {
        return pageHeader(builder, DEFAULT_LINE_COLOR, DEFAULT_NAME_COLOR, name, page, total, size)
    }

    fun pageHeader(builder: StringBuilder, line: String, color: String, name: String, page: Int,
                   total: Int, size: Int): StringBuilder {
        return builder.append(line).append(LINE).append(" ").append(color).append(name).append(" §r[ §b").append(page)
                .append(" §r/ §7").append(total).append(" §r] §d").append(size).append(" ").append(line).append(LINE)
    }

    fun pageInformation(name: String, list: List<Informable>, page: Int, length: Int): Array<String> {
        return pageInformation(DEFAULT_LINE_COLOR, DEFAULT_NAME_COLOR, name, list, page, length)
    }

    fun pageInformation(line: String, color: String, name: String, list: List<Informable>,
                        page: Int, length: Int): Array<String> {
        var page = page
        var length = length
        val size = list.size
        val total = (size - 1) / length

        if (page > total)
            page = total
        else if (page < 0)
            page = 0

        var index = page * 9
        val end = Math.min((page + 1) * length, size)
        length = end - index

        val informations = arrayOfNulls<String>(length + 1)
        //length + 1
        val builder = StringBuilder()
        informations[0] = pageHeader(builder, line, color, name, page + 1, total + 1, size).toString()

        while (index < end) {
            builder.setLength(0)
            list[index].information(index, builder)
            informations[length - (end - index) + 1] = builder.toString()
            index++
        }

        return informations.requireNoNulls()
    }

    fun <T> pageInformation(name: String, list: List<T>, informer: Informer<T>, page: Int, length: Int): Array<String> {
        return pageInformation(DEFAULT_LINE_COLOR, DEFAULT_NAME_COLOR, name, list, informer, page, length)
    }

    fun <T> pageInformation(line: String, color: String, name: String, list: List<T>,
                            informer: Informer<T>, page: Int, length: Int): Array<String> {
        var page = page
        var length = length
        val size = list.size
        val total = (size - 1) / length

        if (page > total)
            page = total
        else if (page < 0)
            page = 0

        var index = page * 9
        val end = Math.min((page + 1) * length, size)
        length = end - index

        var informations = arrayOfNulls<String>(end - index + 1)
        // size : = end - index + 1
        val builder = StringBuilder()
        informations[0] = pageHeader(builder, line, color, name, page + 1, total + 1, size).toString()

        while (index < end) {
            builder.setLength(0)
            informer.information(index, builder, list[index])
            informations[length - (end - index) + 1] = builder.toString()
            index++
        }

        return informations.requireNoNulls()
    }
}