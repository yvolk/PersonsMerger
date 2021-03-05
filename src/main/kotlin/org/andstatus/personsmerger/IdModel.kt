package org.andstatus.personsmerger

interface IdModel {
    fun identify(first: Person, second: Person): IdResult
    fun mutate(): IdModel

    class OneResult(val comparisonEnum: ComparisonEnum, val comparisonWeight: ComparisonWeight)
    
    fun compare(first: Person, second: Person, weights: List<ComparisonWeight>): List<OneResult> {

        return listOf(OneResult(ComparisonEnum.compare(first.firstName, second.firstName), weights[0])) +
                OneResult(ComparisonEnum.compare(first.secondName, second.secondName), weights[1]) +
                OneResult(ComparisonEnum.compare(first.lastName, second.lastName), weights[2]) +
                OneResult(ComparisonEnum.compare(first.birthday, second.birthday), weights[3]) +
                OneResult(ComparisonEnum.compare(first.passport, second.passport), weights[4]) +
                OneResult(ComparisonEnum.compare(first.inp, second.inp), weights[5]) +
                OneResult(ComparisonEnum.compare(first.innRf, second.innRf), weights[6]) +
                OneResult(ComparisonEnum.compare(first.innForeign, second.innForeign), weights[7]) +
                OneResult(ComparisonEnum.compare(first.snils, second.snils), weights[8]) +
                OneResult(ComparisonEnum.compare(first.addressRf, second.addressRf), weights[9]) +
                OneResult(ComparisonEnum.compare(first.addressForeign, second.addressForeign), weights[10])
    }

    fun success(expected: TriResult, idResult: IdResult): Boolean = idResult.result == expected
    fun fitness(expected: TriResult, idResult: IdResult): Long = if (success(expected, idResult)) 1 else 0
}