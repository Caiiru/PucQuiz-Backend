package com.graspfy.graspit.User.Controller.Response

import com.graspfy.graspit.User.User

data class UserResponse (
    val id:Long,
    val email:String,
    val name:String
){
    constructor(user:User):this(user.id!!,user.email,user.name)
}