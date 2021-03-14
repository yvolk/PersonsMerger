package org.andstatus.personsmerger

import kotlin.random.Random

data class IdModelTwo(val weights: List<ComparisonWeight>, val unknownMargin: Int, val trueMargin: Int) : IdModel {
    constructor() : this(defaultWeights(), defaultUnknownMargin, defaultTrueMargin)

    override fun identify(first: Person, second: Person): IdResult {
        val sum = sum(first, second)
        val triResult = TriResult.fromSum(sum, unknownMargin, trueMargin)
        return IdResult(sum, triResult)
    }

    private fun sum(first: Person, second: Person): Int {
        val results = compare(first, second, weights)

        var numerator: Int = 0
        var denominator: Int = 0
        results.forEach {
            when (it.comparisonEnum) {
                ComparisonEnum.EQUAL -> {
                    numerator += it.comparisonWeight.equalWeight
                    denominator += it.comparisonWeight.noneWeight
                }
                ComparisonEnum.DIFFERENT -> {
                    numerator += it.comparisonWeight.differentWeight
                    denominator += it.comparisonWeight.noneWeight
                }
                ComparisonEnum.ONE_ABSENT -> {
                    numerator += it.comparisonWeight.oneAbsentWeight
                    denominator += it.comparisonWeight.noneWeight
                }
                else -> {
                    // numerator += it.comparisonWeight.noneWeight
                }
            }
        }
        return if (denominator == 0) 0 else numerator * 200 / denominator
    }

    override fun mutate(): IdModelTwo {
        val indToMutate = random.nextInt(weights.size)
        val weightToMutate = random.nextInt(3)
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
        private val defaultUnknownMargin: Int = 131
        private val defaultTrueMargin: Int = 163
        private fun defaultWeights(): List<ComparisonWeight> {
            return (0..10).fold(emptyList()) { acc, i ->
                acc + ComparisonWeight(10, 0, 0, 10)
            }
        }

        val trained: IdModelTwo = IdModelTwo(
            weights = listOf(
                ComparisonWeight(equalWeight = 12, differentWeight = -8, oneAbsentWeight = -3, noneWeight = 10),
                ComparisonWeight(equalWeight = 8, differentWeight = -7, oneAbsentWeight = 6, noneWeight = 10),
                ComparisonWeight(equalWeight = 11, differentWeight = -2, oneAbsentWeight = 8, noneWeight = 10),
                ComparisonWeight(equalWeight = 14, differentWeight = 3, oneAbsentWeight = 0, noneWeight = 10),
                ComparisonWeight(equalWeight = 14, differentWeight = -3, oneAbsentWeight = 6, noneWeight = 10),
                ComparisonWeight(equalWeight = 8, differentWeight = -1, oneAbsentWeight = 5, noneWeight = 10),
                ComparisonWeight(equalWeight = 10, differentWeight = -4, oneAbsentWeight = 0, noneWeight = 10),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 10),
                ComparisonWeight(equalWeight = 15, differentWeight = -4, oneAbsentWeight = 9, noneWeight = 10),
                ComparisonWeight(equalWeight = 16, differentWeight = 5, oneAbsentWeight = 10, noneWeight = 10),
                ComparisonWeight(equalWeight = 17, differentWeight = 4, oneAbsentWeight = -4, noneWeight = 10)
            ),
            unknownMargin = 131, trueMargin = 163
        )
    }
}