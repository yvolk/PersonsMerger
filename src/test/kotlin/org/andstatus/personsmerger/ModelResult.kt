package org.andstatus.personsmerger

data class ModelResult(val model: IdModelOne, val personPairs: List<PersonsPair>) {
    val count = personPairs.size
    val successCount: Int = personPairs.count { it.success }
    val failureCount: Int = personPairs.count { it.failure }

    fun print() {
        printSummary()
        personPairs.filter { it.failure }.forEach {
            println("${it.caseId}. ${it.actualSum} failure, expected ${it.expected}, was ${it.actual} ")
        }
        println("----------------------")
        personPairs.forEach {
            println("${it.caseId}. ${it.actualSum} ${ if (it.success) "success" else "failure"}," +
                    " expected ${it.expected}, was ${it.actual} ")
        }
    }

    fun printSummary() {
        if (failureCount == 0) {
            println("Success! $successCount cases")
        } else {
            println("Failed $failureCount of $count cases")
        }
    }

    companion object {
        fun evaluate(model: IdModelOne): ModelResult =
            ModelResult( model, PersonsData.data.fold(emptyList()) { acc, pair ->
                val idResult = mergeDecision(model, pair.first, pair.second)
                acc + pair.copy(actual = idResult.result, actualSum = idResult.sum)
            })

    }
}
