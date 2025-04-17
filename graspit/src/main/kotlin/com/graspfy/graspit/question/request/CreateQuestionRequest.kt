package com.graspfy.graspit.question.request

import com.graspfy.graspit.question.Answer
import com.graspfy.graspit.question.Question
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class CreateQuestionRequest (

    @Pattern(regexp = "^[A-Z][A-Z0-9]+\$")
    val questionText: String? ="question text",

    @NotBlank
    val questionType:Int,
    @NotBlank
    val timeLimit:Int=15,

    val answers: MutableSet<CreateAnswerRequest>?

    ){
    fun toQuestion() = Question(
        question_text = questionText,
        question_type = questionType,
        time_limit = timeLimit,
    )
}