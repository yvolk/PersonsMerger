package org.andstatus.personsmerger

import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun testDefaultWeightsModelOne() {
        val model = IdModelOne()
        val result = ModelResult.evaluate(model)
        result.print()
    }

}