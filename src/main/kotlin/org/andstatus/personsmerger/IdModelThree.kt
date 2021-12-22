package org.andstatus.personsmerger

data class IdModelThree(val model: IdModelOne) : IdModel by model {
    constructor(): this(IdModelOne())

    override fun success(expected: TriResult, idResult: IdResult): Boolean =
        expected == idResult.result

    override fun fitness(expected: TriResult, idResult: IdResult): Long = fitness3(expected, idResult)

    fun fitness3(expected: TriResult, idResult: IdResult): Long = when (expected) {
        TriResult.TRUE -> when (idResult.result) {
            TriResult.FALSE -> 1L * model.trueMargin - (model.trueMargin - idResult.sum)
            TriResult.UNKNOWN -> 100L * model.trueMargin - (model.trueMargin - idResult.sum)
            TriResult.TRUE -> 100000L * model.trueMargin
        }
        TriResult.FALSE -> when (idResult.result) {
            TriResult.FALSE -> 1000000L * model.trueMargin
            TriResult.UNKNOWN -> 10000L * model.trueMargin - (idResult.sum - model.unknownMargin)
            TriResult.TRUE -> 1L * model.trueMargin - (idResult.sum - model.unknownMargin)
        }
        TriResult.UNKNOWN -> when (idResult.result) {
            TriResult.FALSE -> 1000L * model.trueMargin - (model.unknownMargin - idResult.sum)
            TriResult.UNKNOWN -> 100000L * model.trueMargin
            TriResult.TRUE -> 10L * model.trueMargin - (idResult.sum - model.trueMargin)
        }
    }

    fun fitness2(expected: TriResult, idResult: IdResult): Long = when (expected) {
        TriResult.TRUE -> when (idResult.result) {
            TriResult.FALSE -> 1L * model.trueMargin - (model.trueMargin - idResult.sum)
            TriResult.UNKNOWN -> 100L * model.trueMargin - (model.trueMargin - idResult.sum)
            TriResult.TRUE -> 10000L * model.trueMargin
        }
        TriResult.FALSE -> when (idResult.result) {
            TriResult.FALSE -> 10000L * model.trueMargin
            TriResult.UNKNOWN -> 100L * model.trueMargin - (idResult.sum - model.unknownMargin)
            TriResult.TRUE -> 1L * model.trueMargin - (idResult.sum - model.unknownMargin)
        }
        TriResult.UNKNOWN -> when (idResult.result) {
            TriResult.FALSE -> 100L * model.trueMargin - (model.unknownMargin - idResult.sum)
            TriResult.UNKNOWN -> 10000L * model.trueMargin
            TriResult.TRUE -> 100L * model.trueMargin - (idResult.sum - model.trueMargin)
        }
    }

    fun fitness1(expected: TriResult, idResult: IdResult): Long = when(expected) {
        TriResult.TRUE -> when (idResult.result) {
            TriResult.FALSE -> 0
            TriResult.UNKNOWN -> 1000
            TriResult.TRUE -> 1000000
        }
        TriResult.FALSE -> when(idResult.result) {
            TriResult.FALSE -> 1000000
            TriResult.UNKNOWN -> 1000
            TriResult.TRUE -> 0
        }
        TriResult.UNKNOWN -> when(idResult.result) {
            TriResult.FALSE -> 1000
            TriResult.UNKNOWN -> 1000000
            TriResult.TRUE -> 0
        }
    }

    override fun mutate(): IdModel = IdModelThree(model.mutate())

    companion object {
        val trained: IdModelThree = IdModelThree(
            IdModelOne(
                weights = listOf(
                    ComparisonWeight(equalWeight = 18, differentWeight = -12, oneAbsentWeight = 13, noneWeight = -4),
                    ComparisonWeight(equalWeight = 4, differentWeight = -21, oneAbsentWeight = 1, noneWeight = -5),
                    ComparisonWeight(equalWeight = 10, differentWeight = -10, oneAbsentWeight = -7, noneWeight = -9),
                    ComparisonWeight(equalWeight = 10, differentWeight = -9, oneAbsentWeight = -21, noneWeight = -1),
                    ComparisonWeight(equalWeight = 17, differentWeight = -9, oneAbsentWeight = 9, noneWeight = -19),
                    ComparisonWeight(equalWeight = 15, differentWeight = -1, oneAbsentWeight = 11, noneWeight = 11),
                    ComparisonWeight(equalWeight = 16, differentWeight = -9, oneAbsentWeight = -4, noneWeight = -1),
                    ComparisonWeight(equalWeight = 16, differentWeight = -9, oneAbsentWeight = -2, noneWeight = 1),
                    ComparisonWeight(equalWeight = 7, differentWeight = -27, oneAbsentWeight = -1, noneWeight = 2),
                    ComparisonWeight(equalWeight = 21, differentWeight = -4, oneAbsentWeight = 1, noneWeight = 6),
                    ComparisonWeight(equalWeight = 19, differentWeight = -2, oneAbsentWeight = -3, noneWeight = 2)
                ),
                unknownMargin = 40, trueMargin = 60
            )
        )
    }
}