package org.andstatus.personsmerger

import kotlin.random.Random

data class IdModelOne (val weights: List<ComparisonWeight>, val unknownMargin: Int, val trueMargin: Int) : IdModel {
    constructor() : this(defaultWeights(), defaultUnknownMargin, defaultTrueMargin)

    override fun identify(first: Person, second: Person): IdResult {
        val sum = sum(first, second)
        val triResult = TriResult.fromSum(sum, unknownMargin, trueMargin)
        return IdResult(sum, triResult)
    }

    private fun sum(first: Person, second: Person): Int {
        return compare(first, second, weights).sumOf { it.comparisonWeight[it.comparisonEnum.index] }
    }

    override fun mutate(): IdModelOne {
        val indToMutate = random.nextInt(weights.size)
        val weightToMutate = random.nextInt(ComparisonWeight.size)
        val weights = weights.mapIndexed() { index, weight ->
            if (index == indToMutate) weight.withWeightIndex(weightToMutate, mutateInt(weight[weightToMutate]))
            else weight
        }
        return copy(weights = weights)
    }

    private fun mutateInt(value: Int): Int {
        return value + random.nextInt(7) - 3
    }

    companion object {
        val random = Random(1)
        private val defaultUnknownMargin: Int = 40
        private val defaultTrueMargin: Int = 60
        private fun defaultWeights(): List<ComparisonWeight> {
            return (0 .. 10).fold(emptyList()) { acc, i ->
                acc + ComparisonWeight(10, -5, 0, 0)
            }
        }

        val trained: IdModelOne = IdModelOne(
            weights = listOf(
                ComparisonWeight(equalWeight = 10, differentWeight = -15, oneAbsentWeight = -4, noneWeight = 4),
                ComparisonWeight(equalWeight = 10, differentWeight = -6, oneAbsentWeight = 8, noneWeight = 9),
                ComparisonWeight(equalWeight = 10, differentWeight = -6, oneAbsentWeight = 3, noneWeight = -3),
                ComparisonWeight(equalWeight = 11, differentWeight = -3, oneAbsentWeight = -10, noneWeight = 0),
                ComparisonWeight(equalWeight = 16, differentWeight = -6, oneAbsentWeight = -1, noneWeight = -5),
                ComparisonWeight(equalWeight = 7, differentWeight = -5, oneAbsentWeight = 1, noneWeight = 6),
                ComparisonWeight(equalWeight = 15, differentWeight = -5, oneAbsentWeight = 1, noneWeight = 3),
                ComparisonWeight(equalWeight = 13, differentWeight = -7, oneAbsentWeight = -2, noneWeight = 1),
                ComparisonWeight(equalWeight = 12, differentWeight = -11, oneAbsentWeight = 6, noneWeight = 2),
                ComparisonWeight(equalWeight = 10, differentWeight = -7, oneAbsentWeight = -1, noneWeight = 0),
                ComparisonWeight(equalWeight = 11, differentWeight = -7, oneAbsentWeight = -2, noneWeight = 1)
            ),
            unknownMargin = 40, trueMargin = 60
        )
    }
}