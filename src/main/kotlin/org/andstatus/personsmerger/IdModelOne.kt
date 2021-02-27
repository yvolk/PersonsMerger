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
        return value + random.nextInt(15) - 7
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
    }
}