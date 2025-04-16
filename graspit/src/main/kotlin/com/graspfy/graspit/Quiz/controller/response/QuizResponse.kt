package com.graspfy.graspit.Quiz.controller.response

import com.graspfy.graspit.Quiz.Quiz

data class QuizResponse(
    val title:String,
    val createdBy:String,
    val createdAt:String,
){
    constructor(quiz:Quiz):this(quiz.title,
        quiz.createdBy!!.name,quiz.createdAt.toString())
}
