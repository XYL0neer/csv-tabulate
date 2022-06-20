package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CSVReaderTest {
    val sut = CSVReader()

    @Test
    fun readFile_readExistingFile_ListOfLines() {
        val validFilename = "hello.csv"

        val result = sut.readFile(validFilename)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(8, result?.size)
        Assertions.assertEquals("Name;Age;City", result?.get(0))
    }

    @Test
    fun readFile_readNonExistingFile_NoExceptionReturnsNull() {
        val validFilename = "world.csv"

        val result = sut.readFile(validFilename)

        Assertions.assertNull(result)
    }
}