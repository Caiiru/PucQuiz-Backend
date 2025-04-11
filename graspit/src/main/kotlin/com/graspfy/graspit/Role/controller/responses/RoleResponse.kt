package com.graspfy.graspit.Role.controller.responses

import com.graspfy.graspit.Role.Role

data class RoleResponse(
    val name:String,
    val description:String
){
    constructor(role:Role):this(name=role.name,description=role.description)
}
