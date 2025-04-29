package com.graspfy.graspit.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User,Long> {
    fun findByEmail(email:String):User?

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role")
    fun findByRole(role: String): List<User>
}