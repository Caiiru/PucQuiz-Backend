package com.graspfy.graspit.Quiz

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quizzes")
class QuizController(val quizService:QuizService) {
    @PostMapping()
    fun insert(quiz:Quiz):ResponseEntity<Quiz> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(quizService.insertQuiz(quiz));


}