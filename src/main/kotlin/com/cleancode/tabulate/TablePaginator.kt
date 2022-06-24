package com.cleancode.tabulate

import kotlin.math.min

const val TITLE_LINES = 1

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

    fun handleUserAction(action: UserAction, table: List<String>) {
        val tableContentSize = table.size - TITLE_LINES
        when (action) {
            UserAction.FIRST -> setFirstPage()
            UserAction.LAST -> setLastPage(tableContentSize)
            UserAction.NEXT -> incrementPage(tableContentSize)
            UserAction.PREVIOUS -> decrementPage()
            else -> return
        }
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

}
