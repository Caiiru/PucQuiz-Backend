package com.graspfy.graspit.Quiz.controller

import com.graspfy.graspit.Quiz.QuizService
import com.graspfy.graspit.Quiz.controller.Request.CreateQuizRequest
import com.graspfy.graspit.Quiz.controller.Request.PatchQuizRequest
import com.graspfy.graspit.Quiz.controller.response.FullQuizResponse
import com.graspfy.graspit.Quiz.controller.response.QuizResponse
import com.graspfy.graspit.question.request.CreateQuestionRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quizzes")
class QuizController(val quizService: QuizService) {

    @PostMapping
    fun insert(@RequestBody @Valid quiz: CreateQuizRequest,
               @RequestParam @RequestBody userID:Long) =
        FullQuizResponse(quizService.insertQuiz(quiz,userID))
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }


    @GetMapping("/quizzer/{id}")
    fun findAllQuizCreatedBy(@PathVariable id:Long)=
        quizService.findByUserId(id)
            .let { ResponseEntity.ok(it) }

    @GetMapping("{id}")
    fun findByID(@PathVariable id:Long)=
        quizService.findByIDOrNull(id)
            ?.let { ResponseEntity.ok(FullQuizResponse(it)) }
            ?: ResponseEntity.notFound().build()

    @GetMapping
    fun findAll()=
        quizService.findAll()
            .map { QuizResponse(it) }
            .let { ResponseEntity.ok(it) }


    @DeleteMapping("/{id}")
    fun deleteByID(@PathVariable id:Long)=
        quizService.removeQuizById(id)
            .let { ResponseEntity.ok() }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id:Long,
        @Valid @RequestBody quiz: PatchQuizRequest
    ) =
        quizService.update(id,quiz.title)
            ?.let { ResponseEntity.ok(QuizResponse(it)) }
            ?:ResponseEntity.noContent().build()

    @PutMapping("/{id}")
    fun addQuestion(@PathVariable id:Long, @RequestBody @Valid question:CreateQuestionRequest){
        if(quizService.addQuestion(id,question))
            ResponseEntity.ok()
        else
            ResponseEntity.notFound()
    }


}