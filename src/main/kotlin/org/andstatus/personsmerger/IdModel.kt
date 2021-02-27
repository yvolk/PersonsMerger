package org.andstatus.personsmerger

interface IdModel {
    fun identify(first: Person, second: Person): IdResult
    fun mutate(): IdModel
}