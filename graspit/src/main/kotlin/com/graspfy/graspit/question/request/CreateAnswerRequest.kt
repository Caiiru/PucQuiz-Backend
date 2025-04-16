package com.graspfy.graspit.question.request

import com.graspfy.graspit.question.Answer
import jakarta.validation.constraints.Pattern

data class CreateAnswerRequest(

    @Pattern(regexp = "^[A-Z][A-Z0-9]+\$")
    val answerText: String? ="answer text",

    val isCorrect:Boolean = false,

)
{
    fun toAnswer()=Answer(
        answer = answerText,
        isCorrect = isCorrect,
    )
}