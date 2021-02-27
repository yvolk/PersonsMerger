package org.andstatus.personsmerger

data class PersonsPair(val caseId: Long, val first: Person, val second: Person, val expected: TriResult,
                       val actual: TriResult = expected,
                       val actualSum: Int = 0) {
    val success: Boolean get() = expected == actual
    val failure = !success
}

