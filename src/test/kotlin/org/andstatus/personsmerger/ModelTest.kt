package org.andstatus.personsmerger

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ModelTest {

    @Test
    fun learnModelFourShort() {
        val bestResults = Learning.learn(IdModelFour.default, 20)
        val best = bestResults.last()

        assertAll(
            { assertEquals(100, bestResults.size) },
            {
                val failureCount = best.failureCount
                assertTrue(failureCount < 21, "Cases failed: $failureCount")
            },
            {
                assertTrue(best.personPairs.size > 190, "Total cases: ${best.personPairs.size}")
            },
        )
    }

    @Test
    fun testDefaultWeightsModelThree() = testIdModel(IdModelThree(), 71)

    @Test
    fun testTrainedModelThreeFitness7() = testIdModel(IdModelThree.trainedFitness7, 7)

    @Test
    fun testTrainedModelFour() = testIdModel(IdModelFour.trained, 0)

    private fun testIdModel(model: IdModel<*>, numberOfFailures: Int) {
        val result = ModelResult.evaluate(model)
        result.printAll()
        assertEquals(numberOfFailures, result.failureCount, "Number of failures")
    }

}