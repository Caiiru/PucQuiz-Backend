﻿package com.graspfy.graspit

import com.graspfy.graspit.Role.Role
import com.graspfy.graspit.Role.RoleRepository
import com.graspfy.graspit.User.User
import com.graspfy.graspit.User.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Bootstrapper(
    val rolesRepository: RoleRepository,
    val userRepository: UserRepository,
): ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val adminRole = rolesRepository.findByName("admin".uppercase())
            ?:rolesRepository
                .save(Role(name = "ADMIN", description = "System administrator"))
                .also { rolesRepository.save(Role(name="USER", description = "Default User")) }

        if(userRepository.findByRole("ADMIN").isEmpty()){
            val admin = User(
                name = "ADMIN USER",
                email = "admin@gmail.com",
                password = "admin#@!admin"
            )
            admin.roles.add(adminRole)
            userRepository.save(admin)
        }
    }
}