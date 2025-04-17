package com.graspfy.graspit.Quiz

import com.graspfy.graspit.Exception.NotFoundException
import com.graspfy.graspit.Quiz.controller.Request.CreateQuizRequest
import com.graspfy.graspit.User.User
import com.graspfy.graspit.User.UserRepository
import com.graspfy.graspit.question.Question
import com.graspfy.graspit.question.request.CreateQuestionRequest
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class QuizService(private val quizRepository:QuizRepository,
                  private val userRepository: UserRepository){

    fun insertQuiz(quiz:CreateQuizRequest,userID:Long):Quiz {
        val user: User = userRepository.findByIdOrNull(userID) ?: throw NotFoundException("User not found")


        /*
        val questions = quiz.questions?.map {
            question -> Question(question_text=question.questionText,
                question_type = question.questionType,
                time_limit = question.timeLimit,
                answers = question.answers?.map { answer -> answer.toAnswer() }?.toMutableSet())
        }
         */
        val q = Quiz(
            title=quiz.title!!,
            createdAt = ZonedDateTime.now(),
            createdBy = user,
            questions = mutableSetOf(),
        )

        val questions = quiz.questions?.map {
                questionDTO ->
            val question = Question(question_text=questionDTO.questionText,
                question_type = questionDTO.questionType,
                time_limit = questionDTO.timeLimit,
                quiz = q,
                answers = mutableSetOf())

            questionDTO.answers?.forEach{
                answerDTO ->
                val answer = answerDTO.toAnswer().apply {
                    this.question = question
                }
                question.answers?.add(answer)
            }
            question
        }
        q.questions = questions?.toMutableSet()


        return quizRepository.save(q).also { log.info("quiz inserted: {}",q.title) };

    }
    /*
    fun findAllQuizCreatedBy(userID: Long?):List<Quiz> =
        userID?.let{
            quizRepository.findAllQuizCreatedById(userID)
        }

            ?:throw NotFoundException("User not found")
*/
    fun findAll() =quizRepository.findAll()

    fun findByUserId(userId: Long): List<Quiz> {
        val user: User = userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException("User not found")
        return quizRepository.findQuizByUserId(userId)
    }
    fun findByIDOrNull(id: Long): Quiz? {
        return quizRepository.findByIdOrNull(id)
    }

    fun update(quizId:Long, title:String):Quiz?{
        val quiz = quizRepository.findByIdOrNull(quizId) ?: throw NotFoundException("Quiz not found")
        quiz.title = title
        return quizRepository.save(quiz)
    }
    fun removeQuizById(quizId:Long)=quizRepository.deleteById(quizId)

    fun addQuestion(quizId:Long, question: CreateQuestionRequest  ):Boolean{
        val quiz = quizRepository.findByIdOrNull(quizId) ?: throw NotFoundException("Quiz not found")

        /*
        val q=Question(question_text = question.questionText,
            question_type = question.questionType,
            time_limit = question.timeLimit,
            quiz_id = quizId,)

        //answers.forEach { a -> Answer(answer=a.answerText, isCorrect = a.isCorrect, question = q).let { q.answers!!.add(it) } }

        quiz.questions!!.add(q)
        quizRepository.save(quiz)
        */
        return true

    }


    companion object{
        val log = LoggerFactory.getLogger(QuizService::class.java)
    }

}