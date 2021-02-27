package org.andstatus.personsmerger

fun mergeDecision(idModel: IdModel, first: Person, second: Person): IdResult {
    return idModel.identify(first, second)
}