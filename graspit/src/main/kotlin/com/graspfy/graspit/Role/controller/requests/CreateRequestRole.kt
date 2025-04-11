package com.graspfy.graspit.Role.controller.requests

import com.graspfy.graspit.Role.Role
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateRequestRole(
    @Pattern(regexp = "^[A-Z][A-Z0-9]+\$")
    val name:String? = "role name",

    @NotBlank
    val description:String?

) {
    fun toRole() = Role(name = name!!, description = description!!)
}