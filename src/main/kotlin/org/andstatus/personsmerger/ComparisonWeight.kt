package org.andstatus.personsmerger

data class ComparisonWeight(val equalWeight: Int, val differentWeight: Int, val oneAbsentWeight: Int = 0, val noneWeight: Int = 0) {

    fun withWeightIndex(index: Int, value: Int): ComparisonWeight {
        return when(index) {
            0 -> copy( equalWeight = value)
            1 -> copy( differentWeight = value)
            3 -> copy( oneAbsentWeight = value)
            else -> copy( noneWeight = value)
        }
    }

    operator fun get(index: Int): Int = when(index) {
        0 -> equalWeight
        1 -> differentWeight
        3 -> oneAbsentWeight
        else -> noneWeight
    }

    companion object {
        val size = 4
    }
}
