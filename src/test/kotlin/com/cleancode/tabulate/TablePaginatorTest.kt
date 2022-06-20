package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TablePaginatorTest {
    val sut = TablePaginator()

    val table = listOf(
        "|Name     |Age|City     |",
        "+---------+---+---------+",
        "|Peter    |42 |New York |",
        "|Paul     |57 |London   |",
        "|Mary     |35 |Munich   |",
        "|Jaques   |66 |Paris    |",
        "|Yuri     |23 |Moscow   |",
        "|Stephanie|47 |Stockholm|",
        "|Nadia    |29 |Madrid   |",
    )

    val tableContentSize = table.size - 2

    @BeforeEach
    fun before() {
        sut.currentPage = 0
        sut.pageSize = 3
    }

    @Test
    fun incrementPage_IncrementCurrentPage_IncrementedCurrentPage() {
        sut.incrementPage(tableContentSize)

        Assertions.assertEquals(1, sut.currentPage)
    }

    @Test
    fun incrementPage_CannotIncrementOutOfTableSize_CurrentPageIsNotIncremented() {
        sut.currentPage = 2

        sut.incrementPage(tableContentSize)

        Assertions.assertEquals(2, sut.currentPage)
    }

    @Test
    fun decrementPage_DecrementCurrentPage_DecrementedCurrentPage() {
        sut.currentPage = 2

        sut.decrementPage()

        Assertions.assertEquals(1, sut.currentPage)
    }

    @Test
    fun decrementPage_CannotDecrementOutOfTableSize_DerrentPageIsNotDecremented() {
        sut.decrementPage()

        Assertions.assertEquals(0, sut.currentPage)
    }

    @Test
    fun setLastPage_SetCurrentPageToLastPage_CurrentPageIsLastPage() {
        sut.setLastPage(tableContentSize)

        Assertions.assertEquals(2, sut.currentPage)
    }

    @Test
    fun setFirstPage_SetCurrentPageToFirstPage_CurrentPageIsFirstPage() {
        sut.setFirstPage()

        Assertions.assertEquals(0, sut.currentPage)
    }

    @Test
    fun handleUserAction_HandleUserActionFirst_SetToFirstPage() {
        sut.currentPage = 2

        sut.handleUserAction(UserAction.FIRST, table)

        Assertions.assertEquals(0, sut.currentPage)
    }

    @Test
    fun handleUserAction_HandleUserActionNext_IncrementPage() {
        sut.handleUserAction(UserAction.NEXT, table)

        Assertions.assertEquals(1, sut.currentPage)
    }

    @Test
    fun handleUserAction_HandleUserActionPrevious_DecrementPage() {
        sut.currentPage = 2

        sut.handleUserAction(UserAction.PREVIOUS, table)

        Assertions.assertEquals(1, sut.currentPage)
    }

    @Test
    fun handleUserAction_HandleUserActionLast_SetToLastPage() {
        sut.handleUserAction(UserAction.LAST, table)

        Assertions.assertEquals(2, sut.currentPage)
    }

    @Test
    fun handleUserAction_HandleUserActionOther_NothingChanges() {
        sut.handleUserAction(UserAction.CURRENT, table)

        Assertions.assertEquals(0, sut.currentPage)
        Assertions.assertEquals(3, sut.pageSize)
    }

    @Test
    fun startIndex_StartIndexOfPage0_ReturnIndex0() {
        val result = sut.startIndex(tableContentSize)

        Assertions.assertEquals(0, result)
    }

    @Test
    fun startIndex_StartIndexOfPage1_ReturnIndex3() {
        sut.currentPage = 1

        val result = sut.startIndex(tableContentSize)

        Assertions.assertEquals(3, result)
    }

    @Test
    fun endIndex_EndIndexOfPage0_ReturnIndex3() {
        val result = sut.endIndex(tableContentSize)

        Assertions.assertEquals(3, result)
    }

    @Test
    fun endIndex_EndIndexWhenRemainingTableIsSmallerThanPageSize_ReturnIndex7() {
        sut.currentPage = 2

        val result = sut.endIndex(tableContentSize)

        Assertions.assertEquals(tableContentSize, result)
    }

    @Test
    fun buildPaginatedTable_BuildFirstPage_ReturnsFirstPage() {
        val result = sut.buildPaginatedTable(table)

        Assertions.assertEquals(5, result.size)
        Assertions.assertEquals("|Name     |Age|City     |", result[0])
        Assertions.assertEquals("+---------+---+---------+", result[1])
        Assertions.assertEquals("|Peter    |42 |New York |", result[2])
        Assertions.assertEquals("|Paul     |57 |London   |", result[3])
        Assertions.assertEquals("|Mary     |35 |Munich   |", result[4])
    }

    @Test
    fun buildPaginatedTable_BuildLastPage_ReturnsLastPage() {
        sut.currentPage = 2

        val result = sut.buildPaginatedTable(table)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals("|Name     |Age|City     |", result[0])
        Assertions.assertEquals("+---------+---+---------+", result[1])
        Assertions.assertEquals("|Nadia    |29 |Madrid   |", result[2])
    }
}