package com.graspfy.graspit.User

import org.springframework.stereotype.Component

@Component
class UserRepository {
    private val users = mutableMapOf<Long,User>()

    fun save(user:User):User{
        if(user.id == null){
            lastId+=1;
            user.id = lastId;
        }
        users[user.id!!] = user;
        return user;
    }
    companion object{
        private var lastId:Long = 0;
    }
}