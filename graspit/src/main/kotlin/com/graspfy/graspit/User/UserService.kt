package com.graspfy.graspit.User

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun insertUser(user:User)=userRepository.save(user);

    fun getUsers(dir:SortDir):List<User> =
        when(dir) {
            SortDir.ASC -> userRepository.findall().sortedBy { it.name }
            SortDir.DESC -> userRepository.findall().sortedByDescending { it.name }
        }

    fun getUserOrNull(id:Long) = userRepository.findByIdOrNull(id)

    fun removeUserByID(id:Long) = userRepository.deleteByID(id)
}