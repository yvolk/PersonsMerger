package org.andstatus.personsmerger

enum class ComparisonEnum(val index: Int, val isEqual: Boolean = false, val isDifferent: Boolean = false) {
    EQUAL(0, isEqual = true),
    DIFFERENT(1, isDifferent = true),
    ONE_ABSENT(2),
    NONE(3);

    companion object {
        fun compare(first: String?, second: String?): ComparisonEnum {
            if (first.isNullOrEmpty() && second.isNullOrEmpty()) return NONE
            if (first.isNullOrEmpty() || second.isNullOrEmpty()) return ONE_ABSENT
            return if (first == second) EQUAL else DIFFERENT
        }
    }
}