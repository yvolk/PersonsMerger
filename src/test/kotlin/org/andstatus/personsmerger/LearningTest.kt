package org.andstatus.personsmerger

import org.andstatus.personsmerger.Learning.learnModel
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.concurrent.TimeUnit

@Timeout(value = 24, unit = TimeUnit.HOURS)
@Tag("longTests")
class LearningTest {

    @Test
    fun learnModelFour() = learnModel(IdModelFour.default, 3000)

    @Test
    fun learnModelFourTrained() = learnModel(IdModelFour.trained, 3000)

    @Test
    fun learnModelThree() = learnModel(IdModelThree(), 3000)

    @Test
    fun learnModelThreeTrainedFitness7() = learnModel(IdModelThree.trainedFitness7, 1500)

}