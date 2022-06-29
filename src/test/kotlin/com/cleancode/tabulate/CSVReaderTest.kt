package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CSVReaderTest {
    val sut = CSVReader()

    @Test
    fun `readFile readExistingFile ListOfLines`() {
        val validFilename = "hello.csv"

        val result = sut.readFile(validFilename)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(8, result?.size)
        Assertions.assertEquals("No.;Name;Age;City", result?.get(0))
    }

    @Test
    fun `readFile readNonExistingFile NoExceptionReturnsNull`() {
        val validFilename = "world.csv"

        val result = sut.readFile(validFilename)

        Assertions.assertNull(result)
    }

    @Test
    fun `prefixLineWithListing 3Lines linesWithListings`() {
        val lines = mutableListOf("Hello", "World", "My Friends")

        val result = sut.prefixLinesWithListing(lines)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals("No.;Hello", result[0])
        Assertions.assertEquals(" 1.;World", result[1])
        Assertions.assertEquals(" 2.;My Friends", result[2])
    }

    @Test
    fun `prefixLineWithListing onlyHeadline linesWithListings`() {
        val lines = mutableListOf("Hello")

        val result = sut.prefixLinesWithListing(lines)

        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals("No.;Hello", result[0])
    }

    @Test
    fun `prefixLineWithListing emptyList linesWithListings`() {
        val lines = mutableListOf<String>()

        val result = sut.prefixLinesWithListing(lines)

        Assertions.assertEquals(0, result.size)
    }
}