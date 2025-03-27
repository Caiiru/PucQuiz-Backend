package com.graspfy.graspit.User

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun insertUser(user:User)=userRepository.save(user);

}