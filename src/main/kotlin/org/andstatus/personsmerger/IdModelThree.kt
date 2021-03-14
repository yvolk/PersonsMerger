package org.andstatus.personsmerger

data class IdModelThree(val model: IdModelOne) : IdModel by model {
    constructor(): this(IdModelOne())

    override fun success(expected: TriResult, idResult: IdResult): Boolean = when(expected) {
        TriResult.TRUE -> idResult.result == TriResult.TRUE
        else -> idResult.result != TriResult.TRUE
    }

    override fun fitness(expected: TriResult, idResult: IdResult): Long = when(expected) {
        TriResult.TRUE -> if (idResult.result == TriResult.TRUE) 1000000 else 0
        TriResult.FALSE -> when(idResult.result) {
            TriResult.FALSE -> 1000000
            TriResult.UNKNOWN -> 1
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
        val trained: IdModelThree = IdModelThree(IdModelOne(
            weights = listOf(
                ComparisonWeight(equalWeight = 12, differentWeight = -16, oneAbsentWeight = -3, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -6, oneAbsentWeight = 6, noneWeight = 1),
                ComparisonWeight(equalWeight = 10, differentWeight = -11, oneAbsentWeight = -5, noneWeight = 0),
                ComparisonWeight(equalWeight = 13, differentWeight = -4, oneAbsentWeight = -10, noneWeight = -5),
                ComparisonWeight(equalWeight = 15, differentWeight = -9, oneAbsentWeight = 2, noneWeight = -4),
                ComparisonWeight(equalWeight = 7, differentWeight = -5, oneAbsentWeight = -2, noneWeight = 3),
                ComparisonWeight(equalWeight = 14, differentWeight = -12, oneAbsentWeight = -3, noneWeight = 1),
                ComparisonWeight(equalWeight = 16, differentWeight = -8, oneAbsentWeight = 0, noneWeight = 4),
                ComparisonWeight(equalWeight = 13, differentWeight = -13, oneAbsentWeight = 6, noneWeight = 8),
                ComparisonWeight(equalWeight = 16, differentWeight = -3, oneAbsentWeight = 2, noneWeight = 0),
                ComparisonWeight(equalWeight = 15, differentWeight = -5, oneAbsentWeight = 7, noneWeight = -1)
            ),
            unknownMargin = 40, trueMargin = 60
        ))
    }
}