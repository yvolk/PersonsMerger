package org.andstatus.personsmerger

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class ModelTest {

    @Test
    fun testDefaultWeightsModelThree() = testIdModel(IdModelThree())

    @Test
    fun testTrainedModelThree() = testIdModel(IdModelThree.trained, true)

    private fun testIdModel(model: IdModel, shouldBeASuccess: Boolean = false) {
        val result = ModelResult.evaluate(model)
        result.printAll()
        if (shouldBeASuccess) {
            assertTrue(result.isSuccess, "The model should be a success")
        }
    }

}