package org.andstatus.personsmerger

import org.junit.jupiter.api.Test

class MergeTest {

    @Test
    fun testDefaultWeights() {
        val model = IdModelOne()
        val result = ModelResult.evaluate(model)
        result.print()
    }

}