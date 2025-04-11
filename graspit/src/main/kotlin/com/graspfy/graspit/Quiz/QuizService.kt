package com.graspfy.graspit.Quiz

import org.springframework.stereotype.Service

@Service
class QuizService(private val quizRepository:QuizRepository){
    fun insertQuiz(quiz:Quiz) = quizRepository.saveQuiz(quiz)


}