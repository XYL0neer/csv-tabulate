package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CSVTabulateTest {
    val sut = CSVTabulate()

    @Test
    fun tabelliere_ValidCSV_ValidTable() {
        val validString = listOf(
            "Name;Strasse;Ort;Alter",
            "Peter Pan;Am Hang 5;12345 Einsam;42",
            "Maria Schmitz;Kölner Straße 45;50123 Köln;43",
            "Paul Meier;Münchener Weg 1;87654 München;65"
        )

        val result = sut.tabulate(validString)

        Assertions.assertEquals(5, result.size)
        // assert each row
    }

    @Test
    fun parseRows_2LinesWit4Columns_TableWith2Elements() {
        val validLines = listOf(
            "A;B;C;D",
            "a;b;c;d"
        )

        val result = sut.parseRows(validLines)

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals(listOf("A", "B", "C", "D"), result[0])
        Assertions.assertEquals(listOf("a", "b", "c", "d"), result[1])
    }

    @Test
    fun parseRows_2LinesIgnoreTooShortOrLongRows_TableWith2ElementsAnd2Ignored() {
        val invalidLineLengths = listOf(
            "A;B;C;D",
            "a;b;c;d",
            "a;b;c",
            "a;b;c;d;e",
        )

        val result = sut.parseRows(invalidLineLengths)

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals(listOf("A", "B", "C", "D"), result[0])
        Assertions.assertEquals(listOf("a", "b", "c", "d"), result[1])
        Assertions.assertEquals(false, result.contains(listOf("a", "b", "c")))
        Assertions.assertEquals(false, result.contains(listOf("a", "b", "c", "d", "e")))
    }

    @Test
    fun parseRows_emptyList_EmptyTable() {
        val emptyList = emptyList<String>()

        val result = sut.parseRows(emptyList)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun determineColumnWidths_TableWithAllWidth1_ColumnWidthEveryIs1() {
        val tableWidthIsOne = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "b", "c"))

        val result = sut.determineColumnWidths(tableWidthIsOne)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals(1, result[0])
        Assertions.assertEquals(1, result[1])
        Assertions.assertEquals(1, result[2])
    }

    @Test
    fun determineColumnWidths_TableWithDifferentWidths_CorrectColumnWidth() {
        val differentTableWidths = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "bb", "cccc"))

        val result = sut.determineColumnWidths(differentTableWidths)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals(1, result[0])
        Assertions.assertEquals(2, result[1])
        Assertions.assertEquals(4, result[2])
    }

    @Test
    fun determineColumnWidths_EmptyList_EmptyLists() {
        val emptyTable = arrayListOf<ArrayList<String>>()

        val result = sut.determineColumnWidths(emptyTable)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun padColumnsInTable_tableAndColumnList_PaddedTable() {
        val table = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "bb", "cccc"))
        val columnWidth = arrayOf(1, 2, 4)

        sut.padColumnsInTable(table, columnWidth)

        Assertions.assertEquals(2, table.size)
        Assertions.assertEquals(listOf("A", "B ", "C   "), table[0])
        Assertions.assertEquals(listOf("a", "bb", "cccc"), table[1])
    }

    @Test
    fun padColumnsInTable_emptyList_NoExceptionIsThrown() {
        val table = arrayListOf<ArrayList<String>>()
        val columnWidth = emptyArray<Int>()

        sut.padColumnsInTable(table, columnWidth)

        Assertions.assertEquals(0, table.size)
    }

    @Test
    fun mapTableToRows_2Rows_MappedTableRowsWithColumnSeperators() {
        val table = arrayListOf(arrayListOf("A", "B ", "C   "), arrayListOf("a", "bb", "cccc"))

        val result = sut.mapTableToRows(table)

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("|A|B |C   |", result[0])
        Assertions.assertEquals("|a|bb|cccc|", result[1])
    }

    @Test
    fun mapTableToRows_emptyTable_NoExceptionIsThrown() {
        val emptyTable = arrayListOf<ArrayList<String>>()

        val result = sut.mapTableToRows(emptyTable)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun addSeperatorToRows_2Rows_AddedTitleRowSeperator() {
        val rows = arrayListOf("|A|B |C   |", "|a|bb|cccc|")
        val columnWidth = arrayOf(1,2,4)

        sut.addSeperatorToRows(rows, columnWidth)

        Assertions.assertEquals(3, rows.size)
        Assertions.assertEquals("+-+--+----+", rows[1])
    }

    @Test
    fun addSeperatorToRows_emptyRows_NoExceptionIsThrown() {
        val emptyRows = arrayListOf<String>()
        val columnWidth = emptyArray<Int>()

        sut.addSeperatorToRows(emptyRows, columnWidth)

        Assertions.assertEquals(0, emptyRows.size)
    }
}