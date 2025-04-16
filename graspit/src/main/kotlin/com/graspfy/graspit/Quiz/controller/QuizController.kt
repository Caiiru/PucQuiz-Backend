package com.graspfy.graspit.Quiz.controller

import com.graspfy.graspit.Quiz.Quiz
import com.graspfy.graspit.Quiz.QuizService
import com.graspfy.graspit.Quiz.controller.Request.CreateQuizRequest
import com.graspfy.graspit.Quiz.controller.response.QuizResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quizzes")
class QuizController(val quizService: QuizService) {

    @PostMapping()
    fun insert(@RequestBody @Valid quiz: CreateQuizRequest) =
        QuizResponse(quizService.insertQuiz(quiz.toQuiz(),quiz.userID))
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

            //TODO -> FINDALL


    @GetMapping()
    fun findAllQuizCreatedBy(@RequestParam userID:Long)=
        quizService.findByUserId(userID)
            .let { ResponseEntity.ok(it) }



}