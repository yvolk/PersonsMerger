package org.andstatus.personsmerger

enum class NeuronEnum(val index: Int) {
    FirstName(0),
    SecondName(1),
    LastName(2),
    Birthday(3),
    PassportOne(4),
    PassportTwo(5),
    IdOne(6),
    IdTwo(7),
    InsId(8),
    AddressOne(9),
    AddressTwo(10),
    FirstSecondBirth(11),
    Passports(12),
    PassIdOne(13),
    PassIdTwo(14),
    FirstSecondBirthPassTwoNoPassOne(15),
    PassToAddrTwo(16);

    companion object {
        operator fun List<OneResult>.get(neuronEnum: NeuronEnum): OneResult = this[neuronEnum.index]
        operator fun List<ComparisonWeight>.get(neuronEnum: NeuronEnum): ComparisonWeight = this[neuronEnum.index]

        private val list = values().toList()
        fun fromIndex(indexIn: Int): NeuronEnum {
            return list.firstOrNull { it.index == indexIn }
                ?: throw IllegalArgumentException("No enum with index $indexIn")
        }
    }
}