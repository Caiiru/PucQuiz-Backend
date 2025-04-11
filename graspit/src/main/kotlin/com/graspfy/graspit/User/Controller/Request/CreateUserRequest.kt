package com.graspfy.graspit.User.Controller.Request

import com.graspfy.graspit.User.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest (
    @field:Email
    val email:String?,
    @field:NotBlank
    val username:String?,
    @field:NotBlank
    val password:String?,
){
    fun toUser() = User(
        email = email!!,
        name = username ?: "Insert Name",
        password = password !!,
        quizzes = null,
    )
}
