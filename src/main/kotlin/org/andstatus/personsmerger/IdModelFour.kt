package org.andstatus.personsmerger

import org.andstatus.personsmerger.Fitness.fitness7
import org.andstatus.personsmerger.NeuronEnum.*
import org.andstatus.personsmerger.NeuronEnum.Companion.get

data class IdModelFour(override val weights: List<ComparisonWeight>, override val unknownMargin: Int, override val trueMargin: Int) :
    IdModel<IdModelFour> {

    override fun fitness(expected: TriResult, idResult: IdResult): Long = fitness7(expected, idResult)

    override fun copyMe(weightsNew: List<ComparisonWeight>) = copy(weights = weightsNew)

    override fun compare(first: Person, second: Person): List<OneResult> {
        return super.compare(first, second).let { results0 ->
            val results = results0.toMutableList()
            if (results[FirstName].isEqual &&
                results[SecondName].isEqual &&
                results[Birthday].isEqual
            ) results += ComparisonEnum.EQUAL.toResult(FirstSecondBirth)
            if (results[PassportOne].isDifferent &&
                results[PassportTwo].isDifferent
            ) results += ComparisonEnum.DIFFERENT.toResult(Passports)
            if (results[PassportOne].isDifferent &&
                results[IdOne].isDifferent
            ) results += ComparisonEnum.DIFFERENT.toResult(PassIdOne)
            if (results[PassportOne].isDifferent &&
                results[IdTwo].isDifferent
            ) results += ComparisonEnum.DIFFERENT.toResult(PassIdTwo)
            if (results[FirstName].isEqual &&
                results[SecondName].isEqual &&
                results[Birthday].isEqual &&
                results[PassportTwo].isEqual &&
                results[PassportOne].isDifferent
            ) results += ComparisonEnum.EQUAL.toResult(FirstSecondBirthPassTwoNoPassOne)
            if (results[PassportOne].isNotEqual &&
                results[PassportTwo].isNotEqual &&
                results[IdOne].isNotEqual &&
                results[IdTwo].isNotEqual &&
                results[InsId].isNotEqual &&
                results[AddressOne].isNotEqual &&
                results[AddressTwo].isNotEqual
            ) results += ComparisonEnum.DIFFERENT.toResult(PassToAddrTwo)
            results
        }
    }

    companion object {
        val trained: IdModelFour = IdModelFour(
            weights = listOf(
                ComparisonWeight(equalWeight = 8, differentWeight = -81, oneAbsentWeight = -32, noneWeight = -39),
                ComparisonWeight(equalWeight = 20, differentWeight = -36, oneAbsentWeight = -25, noneWeight = 97),
                ComparisonWeight(equalWeight = 52, differentWeight = -51, oneAbsentWeight = 17, noneWeight = -60),
                ComparisonWeight(equalWeight = 60, differentWeight = -19, oneAbsentWeight = 115, noneWeight = -6),
                ComparisonWeight(equalWeight = 24, differentWeight = -135, oneAbsentWeight = 5, noneWeight = -17),
                ComparisonWeight(equalWeight = 20, differentWeight = -1, oneAbsentWeight = -56, noneWeight = 35),
                ComparisonWeight(equalWeight = 95, differentWeight = -119, oneAbsentWeight = 0, noneWeight = -24),
                ComparisonWeight(equalWeight = 84, differentWeight = -117, oneAbsentWeight = -6, noneWeight = -24),
                ComparisonWeight(equalWeight = 73, differentWeight = -85, oneAbsentWeight = -40, noneWeight = -75),
                ComparisonWeight(equalWeight = 7, differentWeight = -19, oneAbsentWeight = -49, noneWeight = 33),
                ComparisonWeight(equalWeight = 6, differentWeight = -21, oneAbsentWeight = 52, noneWeight = 32),
                ComparisonWeight(equalWeight = 149, differentWeight = 0, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -33, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -24, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -9, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 53, differentWeight = 0, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -114, oneAbsentWeight = 0, noneWeight = 0),
            ),
            unknownMargin = -50, trueMargin = 50
        )

        val default: IdModelFour = IdModelFour(
            weights = listOf(
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = 0, oneAbsentWeight = 0, noneWeight = 0), // ИмяОтчДр
                ComparisonWeight(equalWeight = 0, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 0, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
                ComparisonWeight(equalWeight = 10, differentWeight = 0, oneAbsentWeight = 0, noneWeight = 0), // ИмяОтчДрИнп-неПас
                ComparisonWeight(equalWeight = 0, differentWeight = -5, oneAbsentWeight = 0, noneWeight = 0),
            ),
            unknownMargin = -50, trueMargin = 50
        )

    }
}