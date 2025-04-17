package com.graspfy.graspit.Quiz.controller.response

import com.graspfy.graspit.Quiz.Quiz
import com.graspfy.graspit.question.Question
import com.graspfy.graspit.question.response.QuestionResponse

data class FullQuizResponse (
    val id:Long,
    val title:String,
    val createdBy:String,
    val createdAt:String,
    val questions:MutableSet<Question>
){
    constructor(quiz: Quiz):this(
        id=quiz.id,
        quiz.title,
        quiz.createdBy!!.name,
        quiz.createdAt.toString(),
        quiz.questions!!
    )
}