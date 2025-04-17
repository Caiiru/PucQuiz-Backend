package com.graspfy.graspit.Quiz.controller.Request

import com.graspfy.graspit.Quiz.Quiz
import com.graspfy.graspit.question.Question
import com.graspfy.graspit.question.request.CreateQuestionRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class CreateQuizRequest(

    @Pattern(regexp = "^[A-Z][A-Z0-9]+\$")
    val title: String? ="quiz title name",

    var questions:MutableSet<CreateQuestionRequest>?= mutableSetOf()
){
    fun toQuiz() = Quiz(
        title = title!!,
    )
}
