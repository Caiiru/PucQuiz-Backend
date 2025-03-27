package com.graspfy.graspit.User

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @PostMapping("/users")
    fun insert(@RequestBody user:User):User{
        val created = userService.insertUser(user)
        return created;
    }
}