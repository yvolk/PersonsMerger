package org.andstatus.personsmerger

import org.junit.jupiter.api.Test

class Learning {
    @Test
    fun learnModelOne() {
        val firstModel: IdModel = IdModelOne()
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