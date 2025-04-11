package com.graspfy.graspit.Role

import jakarta.persistence.*

@Entity
class Role (
    @Id @GeneratedValue
    val id:Long? = null,
    @Column(unique = true, nullable = false)
    val name:String="Role Name",

    @Column(nullable = false)
    val description:String="",
){
}