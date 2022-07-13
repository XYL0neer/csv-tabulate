package com.cleancode.tabulate

import kotlin.math.ceil
import kotlin.math.min

const val TITLE_LINES = 1
const val DEFAULT_PAGE_SIZE = 3

class TablePaginator(var currentPageIndex: Int = 0, var pageSize: Int = 3) {

    fun buildPaginatedTable(lines: List<String>): List<String> {
        val paginatedTable = lines.take(TITLE_LINES).toMutableList()
        val tableContent = lines.subList(TITLE_LINES, lines.size)
        val start = startIndex(tableContent.size)
        val end = endIndex(tableContent.size)
        paginatedTable.addAll(tableContent.subList(start, end))
        return paginatedTable
    }

    fun startIndex(tableSize: Int) = min(currentPageIndex * pageSize, tableSize)

    fun endIndex(tableSize: Int) = min(pageSize + currentPageIndex * pageSize, tableSize)

    fun handleUserAction(action: Pair<UserAction, Int?>, table: List<String>): Boolean {
        val tableContentSize = table.size - TITLE_LINES
        when (action.first) {
            UserAction.STOP -> return false
            UserAction.FIRST -> setFirstPage()
            UserAction.LAST -> setLastPage(tableContentSize)
            UserAction.NEXT -> incrementPage(tableContentSize)
            UserAction.PREVIOUS -> decrementPage()
            UserAction.JUMP -> jumpToPage(tableContentSize, action.second?.minus(1) ?: 0)
            else -> return true
        }
        return true
    }

    fun setFirstPage() {
        this.currentPageIndex = 0
    }

    fun decrementPage() {
        if (this.currentPageIndex > 0) {
            this.currentPageIndex--
        }
    }

    fun setLastPage(tableSize: Int) {
        this.currentPageIndex = tableSize / pageSize
    }

    fun incrementPage(tableSize: Int) {
        if (this.currentPageIndex < tableSize / pageSize) {
            this.currentPageIndex++
        }
    }

    fun jumpToPage(tableSize: Int, pageIndex: Int) {
        if (pageIndex < 0) {
            setFirstPage()
        } else if (pageIndex >= amountOfPages(tableSize)) {
            setLastPage(tableSize)
        } else {
            this.currentPageIndex = pageIndex
        }
    }

    fun getPageIndex(tableSize: Int): Pair<Int, Int> =
        Pair(currentPageIndex + 1, amountOfPages(tableSize))

    private fun amountOfPages(tableSize: Int): Int = ceil(tableSize.toDouble() / pageSize).toInt()
}
