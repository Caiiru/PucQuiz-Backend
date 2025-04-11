package com.graspfy.graspit.Exception

import org.hibernate.annotations.NotFound
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException (
    message: String = "Not Found",
    cause: Throwable? = null
):IllegalArgumentException(message,cause){
    constructor(id:Long):this("Not found. id=$id")
}