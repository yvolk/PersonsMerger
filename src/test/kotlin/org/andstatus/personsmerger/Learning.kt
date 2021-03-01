package org.andstatus.personsmerger

import org.junit.jupiter.api.Test

class Learning {
    @Test
    fun learnModelOne() {
        learnModel(IdModelOne())
    }

    @Test
    fun learnModelTwo() {
        learnModel(IdModelTwo())
    }

    fun learnModel(firstModel: IdModel) {
        var generationNumber = 1
        var generation = emptyList<ModelResult>()
        var best = emptyList<ModelResult>()
        do {
            val allResults = when (generationNumber) {
                1 -> listOf(ModelResult.evaluate(firstModel))
                else -> mutate(generation.map { it.model }, 10000)
                    .map(ModelResult::evaluate)
                    .sortedBy { it.successCount }
            }
            generation = allResults.takeLast(5000)
            best = (best + generation).sortedBy { it.successCount }.takeLast(1000)

            val sumFrom = allResults.minOfOrNull { it.personPairs.minOfOrNull { it.actualSum } ?: 0 } ?: 0
            val sumTo = allResults.maxOfOrNull { it.personPairs.maxOfOrNull { it.actualSum } ?: 0 } ?: 0

            println("---- Generation $generationNumber ---- from ${allResults.get(allResults.lastIndex).failureCount} " +
                    "to ${allResults.get(0).failureCount}; " +
                    "sum from $sumFrom to $sumTo")
            generation.takeLast(10).forEach {
                it.printSummary()
            }

            generationNumber++
        } while (generationNumber < 100)

        println("---- Best 5 ----")
        best.takeLast(5).forEach {
            it.print()
            println(it.model)
        }

    }

    private fun mutate(models: List<IdModel>, count: Int): List<IdModel> {
        val countOne = count / models.size
        return models.fold(emptyList()) { acc, model ->
            acc + mutate(model, countOne)
        }
    }

    private fun mutate(model: IdModel, count: Int): List<IdModel> {
        return (0 until count).fold(emptyList()) { acc, i ->
            acc + model.mutate()
        }
    }
}