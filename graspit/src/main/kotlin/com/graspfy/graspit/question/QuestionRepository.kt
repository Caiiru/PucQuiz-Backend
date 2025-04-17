package com.graspfy.graspit.question

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository:JpaRepository<Question,Long> {
}