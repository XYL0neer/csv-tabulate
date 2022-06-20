package com.cleancode.tabulate

import java.io.FileNotFoundException
import java.io.FileReader

class CSVReader {
    fun readFile(filename: String): List<String>? {
        return try {
            val file = FileReader(filename)
            file.readLines()
        } catch (error: FileNotFoundException) {
            println("File $filename does not exist")
            null
        }
    }
}