package org.andstatus.personsmerger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

object Learning {

    fun learnModel(firstModel: IdModel<*>, numGenerations: Int) {
        learn(firstModel, numGenerations)
    }

    fun learn(firstModel: IdModel<*>, numGenerations: Int): List<ModelResult> = runBlocking(Dispatchers.Default) {
        var generationNumber = 1
        var generation = emptyList<ModelResult>()
        val bestToKeep = 100
        var best = emptyList<ModelResult>()
        do {
            val allResults: List<ModelResult> = when (generationNumber) {
                1 -> listOf(ModelResult.evaluate(firstModel))
                else -> mutateAndEvaluate(generation)
                    .sortedBy { it.fitness }
            }
            val youngGeneration = allResults.filterNot { it.old }.takeLast(5000 - best.size)
            generation = youngGeneration + best
            best = (best + youngGeneration.map { it.asOld() })
                .sortedBy { it.fitness }
                .distinct()
                .takeLast(bestToKeep)

            val sumFrom = allResults.minOfOrNull { it.personPairs.minOfOrNull { it.actualSum } ?: 0 } ?: 0
            val sumTo = allResults.maxOfOrNull { it.personPairs.maxOfOrNull { it.actualSum } ?: 0 } ?: 0

            val failuresCount = allResults.count { it.failureCount > 0 }
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

        best
    }

    private suspend fun mutateAndEvaluate(generation: List<ModelResult>): List<ModelResult> {
        val childCount = 10000
        val nChunks = 4 // adjust for your computer
        val chunkSize: Int = generation.size.let { if (it > nChunks * 10) it / nChunks else it }
        if (chunkSize == generation.size) {
            return syncMutateAndEvaluate(generation, childCount)
        }
        return coroutineScope {
            val chunks = generation.chunked(chunkSize)
            val childrenForChunk = childCount / chunks.size
            chunks.map { async { syncMutateAndEvaluate(it, childrenForChunk) } }
                .map {
                    it.await()
                }
                .flatten()
        }
    }

    private fun syncMutateAndEvaluate(chunk: List<ModelResult>, countForChunk: Int) =
        mutate(chunk.map { it.model }, countForChunk)
            .map(ModelResult::evaluate)


    private fun mutate(models: List<IdModel<*>>, count: Int): List<IdModel<*>> {
        val countOne = count / models.size
        return models.fold(emptyList()) { acc, model ->
            acc + mutate(model, countOne)
        }
    }

    private fun mutate(model: IdModel<*>, count: Int): List<IdModel<*>> {
        return (0 until count).fold(emptyList()) { acc, i ->
            acc + model.mutate()
        }
    }

}