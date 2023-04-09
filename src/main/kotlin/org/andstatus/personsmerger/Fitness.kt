package org.andstatus.personsmerger

object Fitness {
    fun success(expected: TriResult, idResult: IdResult): Boolean = idResult.result == expected

    fun fitness0(expected: TriResult, idResult: IdResult): Long = if (success(expected, idResult)) 1 else 0

    fun IdModel<*>.fitness6(expected: TriResult, idResult: IdResult): Long {
        val tm = trueMargin
        return when (expected) {
            TriResult.TRUE -> when (idResult.result) {
                TriResult.FALSE -> 1_000L * tm - 10 * (unknownMargin - idResult.sum)
                TriResult.UNKNOWN -> 100_000L * tm - 1_000 * (tm - idResult.sum)
                TriResult.TRUE -> 10_000_000L * tm
            }

            TriResult.FALSE -> when (idResult.result) {
                TriResult.FALSE -> 10_000_000L * tm
                TriResult.UNKNOWN -> 10_000L * tm - 100 * (idResult.sum - unknownMargin)
                TriResult.TRUE -> 10L * tm - (idResult.sum - unknownMargin)
            }

            TriResult.UNKNOWN -> when (idResult.result) {
                TriResult.FALSE -> 1_000_000L * tm - 10_000 * (unknownMargin - idResult.sum)
                TriResult.UNKNOWN -> 10_000_000L * tm
                TriResult.TRUE -> 100L * tm - (idResult.sum - tm)
            }
        }
    }

    fun IdModel<*>.fitness7(expected: TriResult, idResult: IdResult): Long {
        val tm = trueMargin
        return when (expected) {
            TriResult.TRUE -> when (idResult.result) {
                TriResult.FALSE -> -100_000L + 1_000L * idResult.sum
                TriResult.UNKNOWN -> 100_000L + 500L * idResult.sum
                TriResult.TRUE -> 100_000_000L
            }

            TriResult.FALSE -> when (idResult.result) {
                TriResult.FALSE -> 100_000_000L
                TriResult.UNKNOWN -> 1_000_000L - 100L * idResult.sum
                TriResult.TRUE -> -1_000_000_000L - 20_000L * idResult.sum
            }

            TriResult.UNKNOWN -> when (idResult.result) {
                TriResult.FALSE -> 100_000L + 500L * idResult.sum
                TriResult.UNKNOWN -> 100_000_000L
                TriResult.TRUE -> -500_000_000L - 10_000L * idResult.sum
            }
        }
    }
}