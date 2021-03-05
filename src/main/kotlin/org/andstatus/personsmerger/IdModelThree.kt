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
                ComparisonWeight(equalWeight=9, differentWeight=-16, oneAbsentWeight=-2, noneWeight=0),
                ComparisonWeight(equalWeight=12, differentWeight=-4, oneAbsentWeight=9, noneWeight=7),
                ComparisonWeight(equalWeight=10, differentWeight=-9, oneAbsentWeight=-3, noneWeight=-5),
                ComparisonWeight(equalWeight=11, differentWeight=-4, oneAbsentWeight=-12, noneWeight=1),
                ComparisonWeight(equalWeight=18, differentWeight=-5, oneAbsentWeight=-1, noneWeight=-10),
                ComparisonWeight(equalWeight=9, differentWeight=-5, oneAbsentWeight=1, noneWeight=-2),
                ComparisonWeight(equalWeight=15, differentWeight=-8, oneAbsentWeight=-1, noneWeight=-1),
                ComparisonWeight(equalWeight=17, differentWeight=-4, oneAbsentWeight=0, noneWeight=0),
                ComparisonWeight(equalWeight=10, differentWeight=-14, oneAbsentWeight=4, noneWeight=0),
                ComparisonWeight(equalWeight=11, differentWeight=-8, oneAbsentWeight=0, noneWeight=0),
                ComparisonWeight(equalWeight=11, differentWeight=-5, oneAbsentWeight=-4, noneWeight=2)
            ),
            unknownMargin = 40, trueMargin = 60
        ))
    }
}