package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AppTest {

    @Test
    fun `hasCsvFileArg ProvidedCsvFile ReturnsTrue`() {
        val args = arrayOf("hello.csv")
        val sut = App(args)

        val result = sut.hasCsvFileArg()

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `hasCsvFileArg ProvidedNonCsvFile ReturnsFalse`() {
        val args = arrayOf("hello.pdf")
        val sut = App(args)

        val result = sut.hasCsvFileArg()

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `hasCsvFileArg ProvidedNoArgs ReturnsFalse`() {
        val args = emptyArray<String>()
        val sut = App(args)

        val result = sut.hasCsvFileArg()

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `getFilenameFromArgs ProvidedCsvFile ReturnsFilename`() {
        val args = arrayOf("hello.csv")
        val sut = App(args)

        val result = sut.getFilenameFromArgs()

        Assertions.assertEquals("hello.csv", result)
    }

    @Test
    fun `getFilenameFromArgs ProvidedNonCsvFile ReturnsNull`() {
        val args = arrayOf("hello")
        val sut = App(args)

        val result = sut.getFilenameFromArgs()

        Assertions.assertNull(result)
    }

    @Test
    fun `getPageSizeFromArgs providedValidInt returnsProvidedInt`() {
        val args = arrayOf("hello.csv", "10")
        val sut = App(args)

        val result = sut.getPageSizeFromArgs()

        Assertions.assertEquals(10, result)
    }

    @Test
    fun `getPageSizeFromArgs providedNotParsableString returnsDefaultPageSize`() {
        val args = arrayOf("hello.csv", "Definitely not a number!")
        val sut = App(args)

        val result = sut.getPageSizeFromArgs()

        Assertions.assertEquals(DEFAULT_PAGE_SIZE, result)
    }

    @Test
    fun `getPageSizeFromArgs dontProvideAPageSize returnsDefaultPageSize`() {
        val args = arrayOf("hello.csv")
        val sut = App(args)

        val result = sut.getPageSizeFromArgs()

        Assertions.assertEquals(DEFAULT_PAGE_SIZE, result)
    }

    @Test
    fun `resumable AnyUserAction ReturnsTrue`() {
        val sut = App(emptyArray())

        val result = sut.resumable(UserAction.FIRST)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `resumable StopUserAction ReturnsFalse`() {
        val sut = App(emptyArray())

        val result = sut.resumable(UserAction.STOP)

        Assertions.assertEquals(false, result)
    }
}