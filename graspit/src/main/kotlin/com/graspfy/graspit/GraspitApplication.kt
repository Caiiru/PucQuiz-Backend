package com.graspfy.graspit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraspitApplication

fun main(args: Array<String>) {
	runApplication<GraspitApplication>(*args)
}
