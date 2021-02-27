package com.namu.namulibrary.struct

class PagingList<T>(
        private val itemCount: Int,
        private val list: List<T>
) {
    val lastPageIndex = list.size / itemCount + (if (list.size % itemCount >= 1) 1 else 0)

    fun getPage(page: Int): List<T> {
        if (list.isEmpty()) return listOf()
        val startIndex = page * itemCount
        val endIndex = if (list.size > (page + 1) * itemCount) (page + 1) * itemCount else list.size
        return list.subList(startIndex, endIndex)
    }
}