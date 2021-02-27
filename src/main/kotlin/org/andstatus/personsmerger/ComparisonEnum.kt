package org.andstatus.personsmerger

enum class ComparisonEnum(val index: Int) {
    EQUAL(0),
    DIFFERENT(1),
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