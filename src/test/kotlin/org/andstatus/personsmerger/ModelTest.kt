package org.andstatus.personsmerger

import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun testDefaultWeightsModelOne() = testIdModel(IdModelOne())

    @Test
    fun testTrainedModelOne() = testIdModel(IdModelOne.trained)

    @Test
    fun testDefaultWeightsModelTwo() = testIdModel(IdModelTwo())

    @Test
    fun testTrainedModelTwo() = testIdModel(IdModelTwo.trained)

    private fun testIdModel(model: IdModel) {
        val result = ModelResult.evaluate(model)
        result.print()
    }

}