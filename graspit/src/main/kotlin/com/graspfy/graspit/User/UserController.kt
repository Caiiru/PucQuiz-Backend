package com.graspfy.graspit.User

import com.graspfy.graspit.User.Request.UserRequest
import jakarta.validation.Valid
import jakarta.websocket.server.PathParam
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    @PostMapping()
    fun insert(@RequestBody @Valid user:UserRequest):ResponseEntity<User> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.insertUser(user.toUser()))


    @GetMapping()
    fun getAll(sortDir: String?=null)=
            SortDir.entries.firstOrNull{it.name == (sortDir?:"ASC").uppercase()}
                ?.let { userService.getUsers(it) }
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.badRequest().build()

    @GetMapping("/{id}")
    fun getByID(@PathParam(value="id") id:Long) =
        userService.getUserOrNull(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build();

    @DeleteMapping("/{id}")
    fun deleteByID(@PathParam(value="id") id:Long) =
        userService.removeUserByID(id)
            ?.let { ResponseEntity.ok(it) }
            ?:ResponseEntity.notFound().build()
}