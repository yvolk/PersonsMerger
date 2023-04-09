package org.andstatus.personsmerger

import org.andstatus.personsmerger.ComparisonEnum.Companion.compare
import org.andstatus.personsmerger.Fitness.fitness0
import org.andstatus.personsmerger.NeuronEnum.*
import org.andstatus.personsmerger.NeuronEnum.Companion.get
import kotlin.random.Random

interface IdModel<T : IdModel<T>> {
    val weights: List<ComparisonWeight>
    val unknownMargin: Int
    val trueMargin: Int
    fun identify(first: Person, second: Person): IdResult {
        val sum = compare(first, second).sumOf(OneResult::weight)
        val triResult = TriResult.fromSum(sum, unknownMargin, trueMargin)
        return IdResult(sum, triResult)
    }

    fun mutate(): T = oneMutate().oneMutate().oneMutate()

    private fun oneMutate(): T {
        val neuronEnum = getNextRandomNeuronEnum()
        val comparisonEnum = if (neuronEnum == FirstSecondBirth ||
            neuronEnum == FirstSecondBirthPassTwoNoPassOne
        ) {
            ComparisonEnum.EQUAL
        } else if (neuronEnum == Passports ||
            neuronEnum == PassIdOne ||
            neuronEnum == PassIdTwo ||
            neuronEnum == PassToAddrTwo
        ) {
            ComparisonEnum.DIFFERENT
        } else {
            getNextRandomComparisonEnum()
        }
        val weights = weights.mapIndexed() { neuronIndex, weight ->
            if (neuronIndex == neuronEnum.index) weight.withComparisonEnum(
                comparisonEnum,
                mutateInt(comparisonEnum, weight[comparisonEnum])
            )
            else weight
        }
        return copyMe(weights)
    }

    fun copyMe(weightsNew: List<ComparisonWeight>): T

    private fun mutateInt(comparisonEnumToMutate: ComparisonEnum, value: Int): Int {
        repeat(100) {
            val newValue = value + Random.nextInt(31) - 15
            when (comparisonEnumToMutate) {
                ComparisonEnum.EQUAL -> if (newValue >= 0) return newValue
                ComparisonEnum.DIFFERENT -> if (newValue <= 0) return newValue
                ComparisonEnum.ONE_ABSENT -> return newValue
                ComparisonEnum.NONE -> return newValue
            }
        }
        return 0
    }

    fun compare(first: Person, second: Person): List<OneResult> {

        return listOf(
            compare(first.firstName, second.firstName).toResult(FirstName),
            compare(first.secondName, second.secondName).toResult(SecondName),
            compare(first.lastName, second.lastName).toResult(LastName),
            compare(first.birthday, second.birthday).toResult(Birthday),
            compare(first.passportOne, second.passportOne).toResult(PassportOne),
            compare(first.passportTwo, second.passportTwo).toResult(PassportTwo),
            compare(first.idOne, second.idOne).toResult(IdOne),
            compare(first.idTwo, second.idTwo).toResult(IdTwo),
            compare(first.insId, second.insId).toResult(InsId),
            compare(first.addressOne, second.addressOne).toResult(AddressOne),
            compare(first.addressTwo, second.addressTwo).toResult(AddressTwo)
        )
    }

    fun ComparisonEnum.toResult(neuronEnum: NeuronEnum) = OneResult(this, weights[neuronEnum])

    fun fitness(expected: TriResult, idResult: IdResult): Long = fitness0(expected, idResult)

    fun printWeights() {
        weights.forEachIndexed { index, weight ->
            println("$index. ${NeuronEnum.fromIndex(index)}: ${weight.equalWeight}, ${weight.differentWeight}, ${weight.oneAbsentWeight}, ${weight.noneWeight}")
        }
    }

    companion object {
        fun getNextRandomComparisonEnum(): ComparisonEnum = Random.nextInt(ComparisonEnum.values().size)
            .let { ComparisonEnum.values()[it] }

        fun getNextRandomNeuronEnum(): NeuronEnum = Random.nextInt(NeuronEnum.values().size)
            .let { NeuronEnum.values()[it] }

        const val defaultUnknownMargin: Int = -50
        const val defaultTrueMargin: Int = 50
        fun defaultWeights(weightsSize: Int): List<ComparisonWeight> {
            return (0 until weightsSize).fold(emptyList()) { acc, _ ->
                acc + ComparisonWeight(10, -5, 0, 0)
            }
        }
    }
}