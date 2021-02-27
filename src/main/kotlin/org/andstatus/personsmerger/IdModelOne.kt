package org.andstatus.personsmerger

data class IdModelOne(val weights: List<ComparisonWeight>, val unknownMargin: Int, val trueMargin: Int) {
    constructor() : this(defaultWeights(), defaultUnknownMargin, defaultTrueMargin)

    fun identify(first: Person, second: Person): IdResult {
        val sum = sum(first, second)
        val triResult = when {
            sum < unknownMargin -> TriResult.FALSE
            sum < trueMargin -> TriResult.UNKNOWN
            else -> TriResult.TRUE
        }
        return IdResult(sum, triResult)
    }


    fun sum(first: Person, second: Person): Int {
        return compareOne(first.firstName, second.firstName, 0) +
                compareOne(first.secondName, second.secondName, 1) +
                compareOne(first.lastName, second.lastName, 2) +
                compareOne(first.birthday, second.birthday, 3) +
                compareOne(first.passport, second.passport, 4) +
                compareOne(first.inp, second.inp, 5) +
                compareOne(first.innRf, second.innRf, 6) +
                compareOne(first.innForeign, second.innForeign, 7) +
                compareOne(first.snils, second.snils, 8) +
                compareOne(first.addressRf, second.addressRf, 9) +
                compareOne(first.addressForeign, second.addressForeign, 10)
    }

    private fun compareOne(first: String?, second: String?, ind: Int): Int {
        val weight = weights[ind]

        if (first.isNullOrEmpty() && second.isNullOrEmpty()) return weight.noneWeight
        if (first.isNullOrEmpty() || second.isNullOrEmpty()) return weight.oneAbsentWeight
        return if (first == second) weight.equalWeight else weight.differentWeight
    }

    companion object {
        private val defaultUnknownMargin: Int = 40
        private val defaultTrueMargin: Int = 60
        private fun defaultWeights(): List<ComparisonWeight> {
            return (0 .. 10).fold(emptyList()) { acc, i ->
                acc + ComparisonWeight(10, -5, 0, 0)
            }
        }
    }
}