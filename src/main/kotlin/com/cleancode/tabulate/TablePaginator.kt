package com.cleancode.tabulate

import kotlin.math.min


class TablePaginator(var currentPage: Int = 0, var pageSize: Int = 3) {

    fun buildPaginatedTable(table: List<String>): List<String> {
        val paginatedTable = ArrayList(table.subList(0, 2))
        val tableContent = table.subList(2, table.size)
        val start = startIndex(tableContent.size)
        val end = endIndex(tableContent.size)
        paginatedTable.addAll(tableContent.subList(start, end))
        return paginatedTable
    }

    fun startIndex(tableSize: Int) = min(currentPage * pageSize, tableSize)

    fun endIndex(tableSize: Int) = min(pageSize + currentPage * pageSize, tableSize)

    fun handleUserAction(action: UserAction, table: List<String>) {
        val tableContentSize = table.subList(2, table.size).size
        when (action) {
            UserAction.FIRST -> setFirstPage()
            UserAction.LAST -> setLastPage(tableContentSize)
            UserAction.NEXT -> incrementPage(tableContentSize)
            UserAction.PREVIOUS -> decrementPage()
            else -> return
        }
    }

    fun setFirstPage() {
        this.currentPage = 0
    }

    fun decrementPage() {
        if (this.currentPage > 0) {
            this.currentPage--
        }
    }

    fun setLastPage(tableSize: Int) {
        this.currentPage = tableSize / pageSize
    }

    fun incrementPage(tableSize: Int) {
        if (tableSize / pageSize > this.currentPage) {
            this.currentPage++
        }
    }

}
