package org.andstatus.personsmerger

import kotlin.random.Random

data class IdModelTwo (val weights: List<ComparisonWeight>, val unknownMargin: Int, val trueMargin: Int) : IdModel {
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
            when(it.comparisonEnum) {
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
        return if(denominator == 0) 0 else numerator * 200 / denominator
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
            return (0 .. 10).fold(emptyList()) { acc, i ->
                acc + ComparisonWeight(10, 0, 0, 10)
            }
        }
    }
}