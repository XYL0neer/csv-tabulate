package com.cleancode.tabulate

class App {
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


    fun resumable(pageAction: UserAction?): Boolean = pageAction != UserAction.STOP

    fun hasCsvFileArg(args: Array<String>) = args.isNotEmpty() && args[0].contains(".csv")

    fun readUserInput() = readln().lowercase()
}

fun main(args: Array<String>) {
    val app = App()
    if (!app.hasCsvFileArg(args)) {
        println("Please provide a csv file!")
        return
    }
    val filename = args[0]

    val csvReader = CSVReader()
    val csvTabellieren = CSVTabulate()
    val tablePaginator = TablePaginator()
    val lines = csvReader.readFile(filename) ?: return
    val table = csvTabellieren.tabulate(lines)

    var resume = true
    while (resume) {
        tablePaginator.buildPaginatedTable(table).printTable()
        val userInput = app.readUserInput()
        val userAction = app.mapUserInteraction(userInput)
        resume = app.resumable(userAction)
        tablePaginator.handleUserAction(userAction, table)
    }
}

private fun List<String>.printTable() {
    this.forEach { println(it) }
    println("F)irst page, P)revious page, N)ext page, L)ast page, E)xit")
}
