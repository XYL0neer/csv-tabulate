package com.cleancode.tabulate

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UITest {
    val sut = UI()

    @Test
    fun `resumable AnyUserAction ReturnsTrue`() {
        val result = sut.resumable(UserAction.FIRST)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `resumable StopUserAction ReturnsFalse`() {
        val result = sut.resumable(UserAction.STOP)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `mapUserInteraction inputIsFirst UserActionFirst`() {
        val userInput = "f"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.FIRST, result)
    }

    @Test
    fun `mapUserInteraction inputIsPrevious UserActionPrevious`() {
        val userInput = "p"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.PREVIOUS, result)
    }

    @Test
    fun `mapUserInteraction inputIsNext UserActionNext`() {
        val userInput = "n"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.NEXT, result)
    }

    @Test
    fun `mapUserInteraction inputIsLast UserActionLast`() {
        val userInput = "l"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.LAST, result)
    }

    @Test
    fun `mapUserInteraction inputIsExit UserActionStop`() {
        val userInput = "e"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.STOP, result)
    }

    @Test
    fun `mapUserInteraction inputIsRandom UserActionCurrent`() {
        val userInput = "x"

        val result = sut.mapUserInteraction(userInput)

        Assertions.assertEquals(UserAction.CURRENT, result)
    }
}