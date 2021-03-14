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
                OneResult(ComparisonEnum.compare(first.passportOne, second.passportOne), weights[4]) +
                OneResult(ComparisonEnum.compare(first.passportTwo, second.passportTwo), weights[5]) +
                OneResult(ComparisonEnum.compare(first.idOne, second.idOne), weights[6]) +
                OneResult(ComparisonEnum.compare(first.idTwo, second.idTwo), weights[7]) +
                OneResult(ComparisonEnum.compare(first.insId, second.insId), weights[8]) +
                OneResult(ComparisonEnum.compare(first.addressOne, second.addressOne), weights[9]) +
                OneResult(ComparisonEnum.compare(first.addressTwo, second.addressTwo), weights[10])
    }

    fun success(expected: TriResult, idResult: IdResult): Boolean = idResult.result == expected
    fun fitness(expected: TriResult, idResult: IdResult): Long = if (success(expected, idResult)) 1 else 0
}