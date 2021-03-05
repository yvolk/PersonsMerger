package org.andstatus.personsmerger

data class IdResult(val sum: Int, val result: TriResult) {
    val merge: Boolean get() = result == TriResult.TRUE
}
