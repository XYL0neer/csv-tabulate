package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TablePaginatorTest {
    val sut = TablePaginator()

    val lines = listOf(
        "Name;Age;City",
        "Peter;42;New York",
        "Paul;57;London",
        "Mary;35;Munich",
        "Jaques;66;Paris",
        "Yuri;23;Moscow",
        "Stephanie;47;Stockholm",
        "Nadia;29;Madrid",
    )

    val tableContentSize = lines.size - 1

    @BeforeEach
    fun before() {
        sut.currentPageIndex = 0
        sut.pageSize = 3
    }

    @Test
    fun `incrementPage IncrementCurrentPage IncrementedCurrentPage`() {
        sut.incrementPage(tableContentSize)

        Assertions.assertEquals(1, sut.currentPageIndex)
    }

    @Test
    fun `incrementPage CannotIncrementOutOfTableSize CurrentPageIsNotIncremented`() {
        sut.currentPageIndex = 2

        sut.incrementPage(tableContentSize)

        Assertions.assertEquals(2, sut.currentPageIndex)
    }

    @Test
    fun `decrementPage DecrementCurrentPage DecrementedCurrentPage`() {
        sut.currentPageIndex = 2

        sut.decrementPage()

        Assertions.assertEquals(1, sut.currentPageIndex)
    }

    @Test
    fun `decrementPage CannotDecrementOutOfTableSize DerrentPageIsNotDecremented`() {
        sut.decrementPage()

        Assertions.assertEquals(0, sut.currentPageIndex)
    }

    @Test
    fun `setLastPage SetCurrentPageToLastPage CurrentPageIsLastPage`() {
        sut.setLastPage(tableContentSize)

        Assertions.assertEquals(2, sut.currentPageIndex)
    }

    @Test
    fun `setFirstPage SetCurrentPageToFirstPage CurrentPageIsFirstPage`() {
        sut.setFirstPage()

        Assertions.assertEquals(0, sut.currentPageIndex)
    }

    @Test
    fun `handleUserAction HandleUserActionFirst SetToFirstPage`() {
        sut.currentPageIndex = 2

        sut.handleUserAction(UserAction.FIRST, lines)

        Assertions.assertEquals(0, sut.currentPageIndex)
    }

    @Test
    fun `handleUserAction HandleUserActionNext IncrementPage`() {
        sut.handleUserAction(UserAction.NEXT, lines)

        Assertions.assertEquals(1, sut.currentPageIndex)
    }

    @Test
    fun `handleUserAction HandleUserActionPrevious DecrementPage`() {
        sut.currentPageIndex = 2

        sut.handleUserAction(UserAction.PREVIOUS, lines)

        Assertions.assertEquals(1, sut.currentPageIndex)
    }

    @Test
    fun `handleUserAction HandleUserActionLast SetToLastPage`() {
        sut.handleUserAction(UserAction.LAST, lines)

        Assertions.assertEquals(2, sut.currentPageIndex)
    }

    @Test
    fun `handleUserAction HandleUserActionOther NothingChanges`() {
        sut.handleUserAction(UserAction.CURRENT, lines)

        Assertions.assertEquals(0, sut.currentPageIndex)
        Assertions.assertEquals(3, sut.pageSize)
    }

    @Test
    fun `startIndex StartIndexOfPage0 ReturnIndex0`() {
        val result = sut.startIndex(tableContentSize)

        Assertions.assertEquals(0, result)
    }

    @Test
    fun `startIndex StartIndexOfPage1 ReturnIndex3`() {
        sut.currentPageIndex = 1

        val result = sut.startIndex(tableContentSize)

        Assertions.assertEquals(3, result)
    }

    @Test
    fun `endIndex EndIndexOfPage0 ReturnIndex3`() {
        val result = sut.endIndex(tableContentSize)

        Assertions.assertEquals(3, result)
    }

    @Test
    fun `endIndex EndIndexWhenRemainingTableIsSmallerThanPageSize ReturnIndex7`() {
        sut.currentPageIndex = 2

        val result = sut.endIndex(tableContentSize)

        Assertions.assertEquals(tableContentSize, result)
    }

    @Test
    fun `buildPaginatedTable BuildFirstPage ReturnsFirstPage`() {
        val result = sut.buildPaginatedTable(lines)

        Assertions.assertEquals(4, result.size)
        Assertions.assertEquals("Name;Age;City", result[0])
        Assertions.assertEquals("Peter;42;New York", result[1])
        Assertions.assertEquals("Paul;57;London", result[2])
        Assertions.assertEquals("Mary;35;Munich", result[3])
    }

    @Test
    fun `buildPaginatedTable BuildLastPage ReturnsLastPage`() {
        sut.currentPageIndex = 2

        val result = sut.buildPaginatedTable(lines)

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("Name;Age;City", result[0])
        Assertions.assertEquals("Nadia;29;Madrid", result[1])
    }

    @Test
    fun `getPageIndexed 9Elements 1of3`() {
        val result = sut.getPageIndex(9)

        Assertions.assertEquals(1, result.first)
        Assertions.assertEquals(3, result.second)
    }

    @Test
    fun `getPageIndexed 7ElementsGetRoundUp 1of3`() {
        val result = sut.getPageIndex(7)

        Assertions.assertEquals(1, result.first)
        Assertions.assertEquals(3, result.second)
    }
}