package com.graspfy.graspit.User.Controller
import com.graspfy.graspit.Exception.ForbiddenException
import com.graspfy.graspit.Role.Role
import com.graspfy.graspit.User.Controller.Request.CreateUserRequest
import com.graspfy.graspit.User.Controller.Request.LoginRequest
import com.graspfy.graspit.User.Controller.Request.PatchUserRequest
import com.graspfy.graspit.User.Controller.Response.UserResponse
import com.graspfy.graspit.User.SortDir
import com.graspfy.graspit.User.UserService
import com.graspfy.graspit.security.UserToken
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import kotlin.math.log

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {


    @PostMapping
    fun insert(@RequestBody @Valid user: CreateUserRequest):ResponseEntity<UserResponse> =
        UserResponse(userService.insertUser(user.toUser()))
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it)}



    @PatchMapping("/{id}")
    @PreAuthorize("permitAll()")
    @SecurityRequirement(name="GraspAuthServer")
    fun update(
        @PathVariable id:Long,
        @Valid @RequestBody request: PatchUserRequest, auth:Authentication):ResponseEntity<UserResponse> {
        val token = auth.principal as? UserToken?:throw ForbiddenException()
        if(token.id != id && !token.isAdmin) throw ForbiddenException()


        return userService.update(id, request.name!!)
            ?.let { ResponseEntity.ok(UserResponse(it)) }
            ?: ResponseEntity.noContent().build()
    }

    @GetMapping
    fun findAll(@RequestParam sortDir: String?=null,
               @RequestParam role:String? = null) =
        SortDir.entries.firstOrNull{it.name == (sortDir?:"ASC").uppercase()}
            ?.let { userService.findAll(it,role) }
            ?.map {UserResponse(it)}
            ?.let { ResponseEntity.ok(it) }
            ?:ResponseEntity.badRequest()

    @GetMapping("/{id}")
    fun getByID(@PathVariable id:Long) =
        userService.getUserOrNull(id)
            ?.let { ResponseEntity.ok(UserResponse(it)) }
            ?: ResponseEntity.notFound().build()


    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name="GraspAuthServer")
    @DeleteMapping("/{id}")
    fun deleteByID(@PathVariable id:Long) =
        userService.removeUserByID(id)
            .let { ResponseEntity.ok(it) }


    @PutMapping("/{id}/roles/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name="GraspAuthServer")
    fun grant(@PathVariable id:Long, @PathVariable role:String="role name"):ResponseEntity<Void> =
        if (userService.addRole(id,role.uppercase()))
            ResponseEntity.ok().build()
        else
            ResponseEntity.noContent().build()


    @PostMapping("/login")
    fun login(@Valid @RequestBody login:LoginRequest)=
        userService.login(login.email!!,login.password!!)
            ?.let { ResponseEntity.ok(it) }
            ?:ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
}