package com.cleancode.tabulate

class CSVTabulate {
    fun tabulate(csv: List<String>): List<String> {
        val table = parseRows(csv)
        val columnWidths = determineColumnWidths(table)
        padColumnsInTable(table, columnWidths)
        val rows = mapTableToRows(table)
        addSeperatorToRows(rows, columnWidths)
        return rows
    }

    fun parseRows(lines: List<String>): ArrayList<ArrayList<String>> {
        val table = ArrayList<ArrayList<String>>()

        var validColumns: Int? = null
        lines.forEach { row ->
            val elements = row.split(";") as ArrayList<String>
            if (table.isEmpty()) validColumns = elements.size
            if (validColumns != null && elements.size != validColumns!!) return@forEach
            table.add(elements)
        }

        return table
    }

    fun determineColumnWidths(table: ArrayList<ArrayList<String>>): Array<Int> {
        if (table.isEmpty()) return emptyArray()

        val columnWidth = Array(table[0].size) { 0 }
        for (i in 0 until table[0].size) {
            table.forEach { row ->
                if (row[i].length > columnWidth[i]) {
                    columnWidth[i] = row[i].length
                }
            }
        }
        return columnWidth
    }

    fun padColumnsInTable(table:ArrayList<ArrayList<String>>, columnWidth: Array<Int>) {
        for (i in columnWidth.indices) {
            table.forEach { row ->
                row[i] = row[i].padEnd(columnWidth[i], ' ')
            }
        }
    }

    fun mapTableToRows(table: ArrayList<ArrayList<String>>): ArrayList<String> =
        table.map { row -> row.joinToString("|", prefix= "|", postfix = "|") } as ArrayList<String>

    fun addSeperatorToRows(rows: ArrayList<String>, columnWidth: Array<Int>) {
        if (rows.isEmpty()) return
        var seperator = "+"
        columnWidth.map { elWidth ->
            seperator += Array(elWidth) { "-" }.joinToString("")
            seperator += "+"
        }
        rows.add(1, seperator)
    }
}