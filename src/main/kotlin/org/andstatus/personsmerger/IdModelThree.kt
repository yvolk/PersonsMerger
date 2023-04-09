package org.andstatus.personsmerger

import org.andstatus.personsmerger.Fitness.fitness7

data class IdModelThree(
    override val weights: List<ComparisonWeight>,
    override val unknownMargin: Int,
    override val trueMargin: Int
) :
    IdModel<IdModelThree> {
    constructor(weightsSize: Int = 11) : this(
        IdModel.defaultWeights(weightsSize),
        IdModel.defaultUnknownMargin,
        IdModel.defaultTrueMargin
    )

    override fun copyMe(weightsNew: List<ComparisonWeight>) = copy(weights = weightsNew)

    override fun fitness(expected: TriResult, idResult: IdResult): Long = fitness7(expected, idResult)

    companion object {
        val trainedFitness7: IdModelThree = IdModelThree(
            weights = listOf(
                ComparisonWeight(equalWeight = 8, differentWeight = -50, oneAbsentWeight = -17, noneWeight = -26),
                ComparisonWeight(equalWeight = 37, differentWeight = -20, oneAbsentWeight = -15, noneWeight = 37),
                ComparisonWeight(equalWeight = 16, differentWeight = -20, oneAbsentWeight = -60, noneWeight = -50),
                ComparisonWeight(equalWeight = 20, differentWeight = -15, oneAbsentWeight = -278, noneWeight = 103),
                ComparisonWeight(equalWeight = 42, differentWeight = -11, oneAbsentWeight = 119, noneWeight = 57),
                ComparisonWeight(equalWeight = 18, differentWeight = -17, oneAbsentWeight = -11, noneWeight = -39),
                ComparisonWeight(equalWeight = 10, differentWeight = -36, oneAbsentWeight = -17, noneWeight = -23),
                ComparisonWeight(equalWeight = 26, differentWeight = -20, oneAbsentWeight = -2, noneWeight = -8),
                ComparisonWeight(equalWeight = 30, differentWeight = -14, oneAbsentWeight = -6, noneWeight = -52),
                ComparisonWeight(equalWeight = 0, differentWeight = -3, oneAbsentWeight = -49, noneWeight = 13),
                ComparisonWeight(equalWeight = 0, differentWeight = -3, oneAbsentWeight = 16, noneWeight = 14),
            ),
            unknownMargin = -50, trueMargin = 50
        )
    }
}