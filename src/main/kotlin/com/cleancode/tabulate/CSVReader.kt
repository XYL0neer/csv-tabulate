package com.cleancode.tabulate

import java.io.FileNotFoundException
import java.io.FileReader

class CSVReader {
    fun readFile(filename: String): List<String>? {
        return try {
            val file = FileReader(filename)
            val lines = file.readLines()
            return prefixLinesWithListing(lines.toMutableList())
        } catch (error: FileNotFoundException) {
            println("File $filename does not exist")
            null
        }
    }

    fun prefixLinesWithListing(lines: MutableList<String>): List<String> {
        if (lines.isEmpty()) return lines

        lines[0] = "No.;${lines[0]}"
        for (i in 1 until lines.size) {
            lines[i] = " $i.;${lines[i]}"
        }
        return lines
    }
}