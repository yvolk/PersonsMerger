package org.andstatus.personsmerger

enum class TriResult {
    FALSE, UNKNOWN, TRUE;

    companion object {
        fun fromSum(sum: Int, unknownMargin: Int, trueMargin: Int) = when {
            sum < unknownMargin -> FALSE
            sum < trueMargin -> UNKNOWN
            else -> TRUE
        }
    }
}