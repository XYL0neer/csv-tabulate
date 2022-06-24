package com.cleancode.tabulate

const val DEFAULT_PAGE_SIZE = 3

class App(val args: Array<String>) {
    fun hasCsvFileArg() = args.isNotEmpty() && args[0].contains(".csv")
    fun getFilenameFromArgs(): String? {
        if (!hasCsvFileArg()) {
            println("Please provide a csv file!")
            return null
        }
        return args[0]
    }

    fun getPageSizeFromArgs(): Int {
        if (args.isEmpty() || args.size < 2) return DEFAULT_PAGE_SIZE

        return args[1].toIntOrNull() ?: DEFAULT_PAGE_SIZE
    }
}

fun main(args: Array<String>) {
    val app = App(args)
    val filename = app.getFilenameFromArgs() ?: return
    val pageSize = app.getPageSizeFromArgs()

    val csvReader = CSVReader()
    val lines = csvReader.readFile(filename) ?: return

    val ui = UI()
    val csvTabulate = CSVTabulate()
    val tablePaginator = TablePaginator(pageSize = pageSize)
    var resume = true
    while (resume) {
        val paginatedTable = tablePaginator.buildPaginatedTable(lines)
        val tabulatedTable = csvTabulate.tabulate(paginatedTable)
        ui.render(tabulatedTable)

        val userAction = ui.readUserInput()
        tablePaginator.handleUserAction(userAction, lines)
        resume = ui.resumable(userAction)
    }
}
