package org.andstatus.personsmerger

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import kotlin.test.assertTrue

@Timeout(10000)
class Learning {
    @Test
    fun learnModelThree() = learnModel(IdModelThree(), 1500)

    @Test
    fun learnModelThreeTrained() = learnModel(IdModelThree.trained, 1500)

    fun learnModel(firstModel: IdModel, numGenerations: Int) {
        var generationNumber = 1
        var generation = emptyList<ModelResult>()
        var best = emptyList<ModelResult>()
        do {
            val allResults: List<ModelResult> = when (generationNumber) {
                1 -> listOf(ModelResult.evaluate(firstModel))
                else -> mutate(generation.map { it.model }, 10000)
                    .map(ModelResult::evaluate)
                    .sortedBy { it.fitness }
            }
            generation = allResults.takeLast(5000)
            best = (best + generation).sortedBy { it.fitness }.takeLast(1000)

            val sumFrom = allResults.minOfOrNull { it.personPairs.minOfOrNull { it.actualSum } ?: 0 } ?: 0
            val sumTo = allResults.maxOfOrNull { it.personPairs.maxOfOrNull { it.actualSum } ?: 0 } ?: 0

            val failuresCount = allResults.count{ it.failureCount > 0 }
            println(
                "---- Generation $generationNumber ---- " +
                (if (failuresCount == 0) "All succeeded, fitness: " +
                        "${allResults.get(0).fitness} - ${allResults.get(allResults.lastIndex).fitness}"
                    else "Models failed: $failuresCount, Cases failed: " +
                        "${allResults.get(allResults.lastIndex).failureCount} - ${allResults.get(0).failureCount}"
                ) +
                ", " +
                "sums: $sumFrom to $sumTo"
            )
            generation.takeLast(10).forEach {
                it.printSummary()
            }

            if (generationNumber % 50 == 0) {
                println("\n---- The best after $generationNumber generations ----")
                best.lastOrNull()?.printModelAndFailures()
            }

            generationNumber++
        } while (generationNumber <= numGenerations)

        println("\n---- Best 5 ----------")
        best.takeLast(5).forEach {
            println()
            it.printAll()
        }

        assertTrue(best.last().isSuccess, "The best model should be a success")
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