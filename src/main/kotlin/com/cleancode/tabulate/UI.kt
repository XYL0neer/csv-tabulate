package com.cleancode.tabulate

class UI {

    fun render(table: List<String>, pagination: Pair<Int, Int>) {
        table.forEach { println(it) }
        println("Page ${pagination.first} of ${pagination.second}")
        println("F)irst page, P)revious page, N)ext page, L)ast page, J)ump to page, E)xit")
    }


    fun mapUserInteraction(userInput: String): UserAction {
        return when (userInput) {
            "f" -> UserAction.FIRST
            "p" -> UserAction.PREVIOUS
            "n" -> UserAction.NEXT
            "l" -> UserAction.LAST
            "j" -> UserAction.JUMP
            "e" -> UserAction.STOP
            else -> UserAction.CURRENT
        }
    }

    fun readUserInput(): Pair<UserAction, Int?> {
        var pageInput: Int? = null
        val userInput = readln().lowercase()
        val userAction = mapUserInteraction(userInput)
        if (userAction == UserAction.JUMP) {
            print("Jump to page number: ")
             pageInput = readln().toIntOrNull()
            if (pageInput == null) {
                println("Please try again with a number")
            }
        }
        return Pair(userAction, pageInput)
    }
}