package org.andstatus.personsmerger

import org.junit.jupiter.api.Test
import kotlin.random.Random

class Learning {
    val random = Random(1)

    @Test
    fun learn() {
        val model = IdModelOne()
        val result = ModelResult.evaluate(model)

        var generationNumber = 1
        var generation = emptyList<ModelResult>()
        var best = emptyList<ModelResult>()
        do {
            val allResults = when (generationNumber) {
                1 -> listOf(ModelResult.evaluate(model))
                else -> mutate(generation.map { it.model }, 10000)
                    .map(ModelResult::evaluate)
                    .sortedBy { it.successCount }
            }
            generation = allResults.takeLast(5000)
            best = (best + generation).sortedBy { it.successCount }.takeLast(1000)

            println("---- Generation $generationNumber ---- from ${allResults.get(0).successCount} to  ${allResults.get(allResults.lastIndex).successCount}")
            generation.takeLast(10).forEach {
                it.printSummary()
            }

            generationNumber++
        } while (generationNumber < 525)

        println("---- Best 5 ----")
        best.takeLast(5).forEach {
            it.printSummary()
            println(it.model)
        }

    }

    private fun mutate(models: List<IdModelOne>, count: Int): List<IdModelOne> {
        val countOne = count / models.size
        return models.fold(emptyList()) { acc, model ->
            acc + mutate(model, countOne)
        }
    }

    private fun mutate(model: IdModelOne, count: Int): List<IdModelOne> {
        return (0 until count).fold(emptyList()) { acc, i ->
            acc + mutateOne(model)
        }
    }

    private fun mutateOne(model: IdModelOne): IdModelOne {
        val indToMutate = random.nextInt(model.weights.size)
        val weightToMutate = random.nextInt(ComparisonWeight.size)
        val weights = model.weights.mapIndexed() { index, weight ->
            if (index == indToMutate) weight.withWeightIndex(weightToMutate, mutateInt(weight[weightToMutate]))
            else weight
        }
        return model.copy(weights = weights)
    }

    private fun mutateInt(value: Int): Int {
        return value + random.nextInt(15) - 7
    }

}