package com.cleancode.tabulate

const val DEFAULT_PAGE_SIZE = 3

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

    fun parsePageSize(args: Array<String>): Int {
        if (args.isEmpty() || args.size < 2) return DEFAULT_PAGE_SIZE

        return args[1].toIntOrNull() ?: DEFAULT_PAGE_SIZE
    }

    fun readUserInput() = readln().lowercase()
}

fun main(args: Array<String>) {
    val app = App()
    if (!app.hasCsvFileArg(args)) {
        println("Please provide a csv file!")
        return
    }
    val filename = args[0]
    val pageSize = app.parsePageSize(args)

    val csvReader = CSVReader()
    val lines = csvReader.readFile(filename) ?: return

    val csvTabulate = CSVTabulate()
    val tablePaginator = TablePaginator(pageSize = pageSize)
    var resume = true
    while (resume) {
        tablePaginator.buildPaginatedTable(lines).let { paginatedLines ->
            csvTabulate.tabulate(paginatedLines).printTable()
        }
        val userInput = app.readUserInput()
        val userAction = app.mapUserInteraction(userInput)
        tablePaginator.handleUserAction(userAction, lines)
        resume = app.resumable(userAction)
    }
}

private fun List<String>.printTable() {
    this.forEach { println(it) }
    println("F)irst page, P)revious page, N)ext page, L)ast page, E)xit")
}
