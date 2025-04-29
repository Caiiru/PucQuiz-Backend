package com.graspfy.graspit.User.Controller.Response

data class LoginResponse (
    val token:String,
    val user:UserResponse
)