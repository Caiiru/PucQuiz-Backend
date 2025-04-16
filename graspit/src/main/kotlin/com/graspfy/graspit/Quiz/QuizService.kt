package com.graspfy.graspit.Quiz

import com.graspfy.graspit.Exception.NotFoundException
import com.graspfy.graspit.User.User
import com.graspfy.graspit.User.UserRepository
import com.graspfy.graspit.User.UserService
import com.graspfy.graspit.question.Answer
import com.graspfy.graspit.question.Question
import com.graspfy.graspit.question.request.CreateAnswerRequest
import com.graspfy.graspit.question.request.CreateQuestionRequest
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class QuizService(private val quizRepository:QuizRepository,
                  private val userRepository: UserRepository){

    fun insertQuiz(quiz:Quiz,userID:Long):Quiz {
        val user: User = userRepository.findByIdOrNull(userID) ?: throw NotFoundException("User not found")
        quiz.setCreator(user)
        return quizRepository.save(quiz).also { log.info("quiz inserted: {}",quiz.title) };

    }
    /*
    fun findAllQuizCreatedBy(userID: Long?):List<Quiz> =
        userID?.let{
            quizRepository.findAllQuizCreatedById(userID)
        }

            ?:throw NotFoundException("User not found")
*/
    fun findByUserId(userId: Long): List<Quiz> {
        val user: User = userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException("User not found")
        return quizRepository.findQuizByUserId(userId)
    }

    fun update(quizId:Long, title:String):Quiz?{
        val quiz = quizRepository.findByIdOrNull(quizId) ?: throw NotFoundException("Quiz not found")
        quiz.title = title
        return quizRepository.save(quiz)
    }
    fun removeQuizById(quizId:Long)=quizRepository.deleteById(quizId)

    fun addQuestion(quizId:Long, question: CreateQuestionRequest, answers:List<CreateAnswerRequest>):Boolean{
        val quiz = quizRepository.findByIdOrNull(quizId) ?: throw NotFoundException("Quiz not found")

        val q=Question(question_text = question.questionText,
            question_type = question.questionType,
            time_limit = question.timeLimit,
            quiz_id = quizId,)

        answers.forEach { a -> Answer(answer=a.answerText, isCorrect = a.isCorrect, question = q).let { q.answers!!.add(it) } }

        quiz.questions!!.add(q)
        quizRepository.save(quiz)
        return true

    }


    companion object{
        val log = LoggerFactory.getLogger(QuizService::class.java)
    }

}