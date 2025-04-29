package com.graspfy.graspit.Role.controller

import com.graspfy.graspit.Role.Role
import com.graspfy.graspit.Role.RoleService
import com.graspfy.graspit.Role.controller.requests.CreateRequestRole
import com.graspfy.graspit.Role.controller.responses.RoleResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name="GraspAuthServer")
class RoleController (val service: RoleService){
    @PostMapping
    fun insert(@Valid @RequestBody role:CreateRequestRole)=RoleResponse(service.insert(role.toRole()))
        .let {
            ResponseEntity.status(HttpStatus.CREATED).body(it)
        }

    @GetMapping
    fun listAll() = service.findAll()
        .map { RoleResponse(it) }
        .let { ResponseEntity.ok(it) }
}