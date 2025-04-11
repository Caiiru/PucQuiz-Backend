package com.graspfy.graspit.User.Controller.Request

import jakarta.validation.constraints.NotBlank

data class PatchUserRequest (
    @field:NotBlank
    val name: String?
)