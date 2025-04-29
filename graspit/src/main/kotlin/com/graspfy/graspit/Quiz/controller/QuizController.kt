package com.graspfy.graspit.Quiz.controller

import com.graspfy.graspit.Exception.ForbiddenException
import com.graspfy.graspit.Quiz.QuizService
import com.graspfy.graspit.Quiz.controller.Request.CreateQuizRequest
import com.graspfy.graspit.Quiz.controller.Request.PatchQuizRequest
import com.graspfy.graspit.Quiz.controller.response.FullQuizResponse
import com.graspfy.graspit.Quiz.controller.response.QuizResponse
import com.graspfy.graspit.question.request.CreateQuestionRequest
import com.graspfy.graspit.security.UserToken
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
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
    @PreAuthorize("permitAll()")
    @SecurityRequirement(name="GraspAuthServer")
    fun insert(@RequestBody @Valid quiz: CreateQuizRequest,
               @RequestParam @RequestBody userID:Long) =
        FullQuizResponse(quizService.insertQuiz(quiz,userID))
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }


    @GetMapping("/user/{id}")
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


    @PreAuthorize("permitAll()")
    @SecurityRequirement(name="GraspAuthServer")
    @DeleteMapping("/{id}")
    fun deleteByID(@PathVariable id:Long, auth: Authentication) {
        val token = auth.principal as? UserToken ?: throw ForbiddenException()
        if(token.id != id && !token.isAdmin) throw ForbiddenException()

        quizService.removeQuizById(id)
            .let { ResponseEntity.ok() }
    }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id:Long,
        @Valid @RequestBody quiz: PatchQuizRequest,
        auth:Authentication
    ) {
        val token = auth.principal as? UserToken ?: throw ForbiddenException()
        if(token.id != id && !token.isAdmin) throw ForbiddenException()

        quizService.update(id, quiz.title)
            ?.let { ResponseEntity.ok(QuizResponse(it)) }
            ?: ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun addQuestion(@PathVariable id:Long, @RequestBody @Valid question:CreateQuestionRequest, auth:Authentication)
    {
        val token = auth.principal as? UserToken ?: throw ForbiddenException()
        if(token.id != id && !token.isAdmin) throw ForbiddenException()

        if(quizService.addQuestion(id,question))
            ResponseEntity.ok()
        else
            ResponseEntity.notFound()
    }


}