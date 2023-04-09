package org.andstatus.personsmerger

class OneResult(val comparisonEnum: ComparisonEnum, val comparisonWeight: ComparisonWeight) {
    val isEqual: Boolean get() = comparisonEnum.isEqual
    val isDifferent: Boolean get() = comparisonEnum.isDifferent
    val isNotEqual: Boolean get() = !comparisonEnum.isEqual
    val weight: Int get() = comparisonWeight[comparisonEnum]
}