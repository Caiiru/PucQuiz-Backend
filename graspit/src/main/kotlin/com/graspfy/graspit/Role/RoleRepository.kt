package com.graspfy.graspit.Role

import com.graspfy.graspit.User.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository:JpaRepository<Role,Long> {
    fun findByName(name:String):Role?

}