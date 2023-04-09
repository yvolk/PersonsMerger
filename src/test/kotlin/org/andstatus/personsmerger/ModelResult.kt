package org.andstatus.personsmerger

import org.andstatus.personsmerger.Fitness.success

data class ModelResult(val model: IdModel<*>, val personPairs: List<PersonsPair>, val old: Boolean = false) {
    val fitness: Long = personPairs.sumOf { it.fitness }
    val failureCount: Int get() = personPairs.count { it.failure }
    val isSuccess: Boolean get() = failureCount == 0

    fun asOld(): ModelResult = copy(old = true)

    fun printAll() {
        printModelAndFailures()
        println("---- all -------------")
        personPairs.forEach {
            println(
                "${it.caseId}. ${it.actualSum} ${if (it.success) "success" else "failure"}," +
                        " expected ${it.expected}, was ${it.actual} "
            )
        }
    }

    fun printModelAndFailures() {
        printSummary()
        println(model)
        model.printWeights()
        if (failureCount > 0) {
            println("---- failures --------")
            personPairs.filter { it.failure }.forEach {
                println("${it.caseId}. ${it.actualSum} failure, expected ${it.expected}, was ${it.actual} ")
            }
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
        fun evaluate(model: IdModel<*>): ModelResult =
            ModelResult(model, PersonsData.data.fold(emptyList()) { acc, pair ->
                val idResult = model.identify(pair.first, pair.second)
                acc + pair.copy(
                    actual = idResult.result, actualSum = idResult.sum,
                    success = success(pair.expected, idResult),
                    fitness = model.fitness(pair.expected, idResult)
                )
            })

    }

    override fun equals(other: Any?): Boolean {
        if (other is ModelResult) return model.equals(other.model)
        return false
    }
}
