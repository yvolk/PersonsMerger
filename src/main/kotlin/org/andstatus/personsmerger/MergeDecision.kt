package org.andstatus.personsmerger

fun mergeDecision(idModel: IdModelOne, first: Person, second: Person): IdResult {
    return idModel.identify(first, second)
}