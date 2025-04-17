package com.graspfy.graspit.question.response

import com.graspfy.graspit.question.Answer
import com.graspfy.graspit.question.Question

data class QuestionResponse(
    val id: Long?,
    val text: String,
    val questionType:Int,
    val timeLimit:Int,
    val quizID:Long,
    val answers: MutableSet<Answer>
){
    constructor(question: Question) : this(
         question.id,
        question.question_text!!,
        question.question_type!!,
        question.time_limit,
        question.quiz!!.id,
        question.answers!!
    )
}