package com.graspfy.graspit.Quiz

import com.graspfy.graspit.Exception.NotFoundException
import com.graspfy.graspit.User.User
import com.graspfy.graspit.User.UserRepository
import com.graspfy.graspit.User.UserService
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
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




    companion object{
        val log = LoggerFactory.getLogger(QuizService::class.java)
    }

}