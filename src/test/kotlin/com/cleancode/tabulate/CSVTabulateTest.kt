package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CSVTabulateTest {
    val sut = CSVTabulate()

    @Test
    fun `tabelliere ValidCSV ValidTable`() {
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
    fun `parseRows 2LinesWit4Columns TableWith2Elements`() {
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
    fun `parseRows 2LinesIgnoreTooShortOrLongRows TableWith2ElementsAnd2Ignored`() {
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
    fun `parseRows emptyList EmptyTable`() {
        val emptyList = emptyList<String>()

        val result = sut.parseRows(emptyList)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `determineColumnWidths TableWithAllWidth1 ColumnWidthEveryIs1`() {
        val tableWidthIsOne = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "b", "c"))

        val result = sut.determineColumnWidths(tableWidthIsOne)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals(1, result[0])
        Assertions.assertEquals(1, result[1])
        Assertions.assertEquals(1, result[2])
    }

    @Test
    fun `determineColumnWidths TableWithDifferentWidths CorrectColumnWidth`() {
        val differentTableWidths = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "bb", "cccc"))

        val result = sut.determineColumnWidths(differentTableWidths)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals(1, result[0])
        Assertions.assertEquals(2, result[1])
        Assertions.assertEquals(4, result[2])
    }

    @Test
    fun `determineColumnWidths EmptyList EmptyLists`() {
        val emptyTable = arrayListOf<ArrayList<String>>()

        val result = sut.determineColumnWidths(emptyTable)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `padColumnsInTable tableAndColumnList PaddedTable`() {
        val table = arrayListOf(arrayListOf("A", "B", "C"), arrayListOf("a", "bb", "cccc"))
        val columnWidth = arrayOf(1, 2, 4)

        sut.padColumnsInTable(table, columnWidth)

        Assertions.assertEquals(2, table.size)
        Assertions.assertEquals(listOf("A", "B ", "C   "), table[0])
        Assertions.assertEquals(listOf("a", "bb", "cccc"), table[1])
    }

    @Test
    fun `padColumnsInTable emptyList NoExceptionIsThrown`() {
        val table = arrayListOf<ArrayList<String>>()
        val columnWidth = emptyArray<Int>()

        sut.padColumnsInTable(table, columnWidth)

        Assertions.assertEquals(0, table.size)
    }

    @Test
    fun `mapTableToRows 2Rows MappedTableRowsWithColumnSeperators`() {
        val table = arrayListOf(arrayListOf("A", "B ", "C   "), arrayListOf("a", "bb", "cccc"))

        val result = sut.mapTableToRows(table)

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("|A|B |C   |", result[0])
        Assertions.assertEquals("|a|bb|cccc|", result[1])
    }

    @Test
    fun `mapTableToRows emptyTable NoExceptionIsThrown`() {
        val emptyTable = arrayListOf<ArrayList<String>>()

        val result = sut.mapTableToRows(emptyTable)

        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun `addSeperatorToRows 2Rows AddedTitleRowSeperator`() {
        val rows = arrayListOf("|A|B |C   |", "|a|bb|cccc|")
        val columnWidth = arrayOf(1,2,4)

        sut.addSeperatorToRows(rows, columnWidth)

        Assertions.assertEquals(3, rows.size)
        Assertions.assertEquals("+-+--+----+", rows[1])
    }

    @Test
    fun `addSeperatorToRows emptyRows NoExceptionIsThrown`() {
        val emptyRows = arrayListOf<String>()
        val columnWidth = emptyArray<Int>()

        sut.addSeperatorToRows(emptyRows, columnWidth)

        Assertions.assertEquals(0, emptyRows.size)
    }
}