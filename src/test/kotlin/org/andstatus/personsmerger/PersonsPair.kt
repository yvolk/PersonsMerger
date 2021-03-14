package org.andstatus.personsmerger

data class PersonsPair(val caseId: Long, val first: Person, val second: Person, val expected: TriResult,
                       val actual: TriResult = expected,
                       val actualSum: Int = 0,
                       val success: Boolean = true,
                       val fitness: Long = 0) {
    val failure = !success

    companion object {
        fun printAsCsv(personsData: List<PersonsPair>) {
            println("id,expected,firstName,secondName,lastName,birthday,passportOne,passportTwo,idOne,idTwo,insId,addressOne,addressTwo")
            for (d in personsData) {
                with(d.first) {
                    println("${d.caseId},${d.expected},$firstName,$secondName,$lastName,$birthday,$passportOne,$passportTwo,$idOne,$idTwo,$insId,$addressOne,$addressTwo")
                }
                with(d.second) {
                    println("-,-,$firstName,$secondName,$lastName,$birthday,$passportOne,$passportTwo,$idOne,$idTwo,$insId,$addressOne,$addressTwo")
                }
            }
        }
    }
}

