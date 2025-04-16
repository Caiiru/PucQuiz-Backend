package com.graspfy.graspit.Quiz

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface QuizRepository:JpaRepository<Quiz,Long> {

    @Query("select q from Quiz q where q.createdBy.id = :userId")
    fun findQuizByUserId(userId: Long): List<Quiz>



}