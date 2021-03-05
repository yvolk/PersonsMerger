package org.andstatus.personsmerger

data class ModelResult(val model: IdModel, val personPairs: List<PersonsPair>) {
    val fitness: Long = personPairs.sumOf { it.fitness }
    val failureCount: Int get() = personPairs.count { it.failure }
    val isSuccess: Boolean get() = failureCount == 0

    fun print() {
        printSummary()
        println(model)
        if (failureCount > 0) {
            println("---- failures --------")
            personPairs.filter { it.failure }.forEach {
                println("${it.caseId}. ${it.actualSum} failure, expected ${it.expected}, was ${it.actual} ")
            }
        }
        println("---- all -------------")
        personPairs.forEach {
            println("${it.caseId}. ${it.actualSum} ${ if (it.success) "success" else "failure"}," +
                    " expected ${it.expected}, was ${it.actual} ")
        }
    }

    fun printSummary() {
        val sumsText = "fitness: $fitness, sums: ${personPairs.minOf { it.actualSum }} to ${personPairs.maxOf { it.actualSum }}"
        if (failureCount == 0) {
            println("Success, $sumsText")
        } else {
            println("Failed  $failureCount of ${personPairs.size} cases, $sumsText")
        }
    }

    companion object {
        fun evaluate(model: IdModel): ModelResult =
            ModelResult(model, PersonsData.data.fold(emptyList()) { acc, pair ->
                val idResult = model.identify(pair.first, pair.second)
                acc + pair.copy(actual = idResult.result, actualSum = idResult.sum,
                success = model.success(pair.expected, idResult),
                fitness = model.fitness(pair.expected, idResult))
            })

    }
}
