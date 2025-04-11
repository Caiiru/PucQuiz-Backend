package com.graspfy.graspit.Quiz

import org.springframework.stereotype.Repository

@Repository
class QuizRepository {
    var quizzes = mutableMapOf<Long,Quiz>();

    fun saveQuiz(quiz:Quiz):Quiz{
        if(quiz.id == null){
            quiz.id = lastID;
        }
        quizzes[quiz.id] = quiz;
        return quizzes[quiz.id]!!;
    }

    companion object{
        var lastID:Long = 0;
    }
}