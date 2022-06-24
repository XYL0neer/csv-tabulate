package com.cleancode.tabulate

class UI {

    fun render(table: List<String>) {
        table.forEach { println(it) }
        println("F)irst page, P)revious page, N)ext page, L)ast page, E)xit")
    }

    fun resumable(pageAction: UserAction?): Boolean = pageAction != UserAction.STOP

    fun mapUserInteraction(userInput: String): UserAction {
        return when (userInput) {
            "f" -> UserAction.FIRST
            "p" -> UserAction.PREVIOUS
            "n" -> UserAction.NEXT
            "l" -> UserAction.LAST
            "e" -> UserAction.STOP
            else -> UserAction.CURRENT
        }
    }

    fun readUserInput(): UserAction {
        val userInput = readln().lowercase()
        return mapUserInteraction(userInput)
    }
}