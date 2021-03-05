package org.andstatus.personsmerger

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class ModelTest {

    @Test
    fun testDefaultWeightsModelOne() = testIdModel(IdModelOne())

    @Test
    fun testTrainedModelOne() = testIdModel(IdModelOne.trained, true)

    @Test
    fun testDefaultWeightsModelTwo() = testIdModel(IdModelTwo())

    @Test
    fun testTrainedModelTwo() = testIdModel(IdModelTwo.trained, true)

    @Test
    fun testDefaultWeightsModelThree() = testIdModel(IdModelThree())

    @Test
    fun testTrainedModelThree() = testIdModel(IdModelThree.trained, true)

    private fun testIdModel(model: IdModel, shouldBeASuccess: Boolean = false) {
        val result = ModelResult.evaluate(model)
        result.print()
        if (shouldBeASuccess) {
            assertTrue(result.isSuccess, "The model should be a success")
        }
    }

}