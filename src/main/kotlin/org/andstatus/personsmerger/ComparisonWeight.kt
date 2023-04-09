package org.andstatus.personsmerger

data class ComparisonWeight(val equalWeight: Int, val differentWeight: Int, val oneAbsentWeight: Int = 0, val noneWeight: Int = 0) {

    fun withComparisonEnum(comparisonEnum: ComparisonEnum, value: Int): ComparisonWeight {
        return when (comparisonEnum) {
            ComparisonEnum.EQUAL -> copy(equalWeight = value)
            ComparisonEnum.DIFFERENT -> copy(differentWeight = value)
            ComparisonEnum.ONE_ABSENT -> copy(oneAbsentWeight = value)
            else -> copy(noneWeight = value)
        }
    }

    operator fun get(index: ComparisonEnum): Int = when (index) {
        ComparisonEnum.EQUAL -> equalWeight
        ComparisonEnum.DIFFERENT -> differentWeight
        ComparisonEnum.ONE_ABSENT -> oneAbsentWeight
        else -> noneWeight
    }

    operator fun get(index: Int): Int = when (index) {
        0 -> equalWeight
        1 -> differentWeight
        2 -> oneAbsentWeight
        else -> noneWeight
    }

    companion object {
        const val size = 4
    }
}
