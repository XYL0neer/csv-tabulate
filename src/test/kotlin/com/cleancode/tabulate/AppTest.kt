package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AppTest {
    val sut = App()

    @Test
    fun resumable_AnyUserAction_ReturnsTrue() {
        val result = sut.resumable(UserAction.FIRST)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun resumable_StopUserAction_ReturnsFalse() {
        val result = sut.resumable(UserAction.STOP)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun hasCsvFileArg_ProvidedCsvFile_ReturnsTrue() {
        val args = arrayOf("hello.csv")

        val result = sut.hasCsvFileArg(args)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun hasCsvFileArg_ProvidedNonCsvFile_ReturnsFalse() {
        val args = arrayOf("hello.pdf")

        val result = sut.hasCsvFileArg(args)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun hasCsvFileArg_ProvidedNoArgs_ReturnsFalse() {
        val args = emptyArray<String>()

        val result = sut.hasCsvFileArg(args)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun mapUserInteraction_inputIsf_UserActionFirst() {
        val userInput = "f"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.FIRST, result)
    }

    @Test
    fun mapUserInteraction_inputIsp_UserActionPrevious() {
        val userInput = "p"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.PREVIOUS, result)
    }

    @Test
    fun mapUserInteraction_inputIsn_UserActionNext() {
        val userInput = "n"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.NEXT, result)
    }

    @Test
    fun mapUserInteraction_inputIsl_UserActionLast() {
        val userInput = "l"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.LAST, result)
    }

    @Test
    fun mapUserInteraction_inputIse_UserActionStop() {
        val userInput = "e"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.STOP, result)
    }

    @Test
    fun mapUserInteraction_inputIsx_UserActionCurrent() {
        val userInput = "x"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.CURRENT, result)
    }
}