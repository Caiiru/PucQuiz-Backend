package com.graspfy.graspit.Exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(
    message:String = "Forbidden",
    cause:Throwable?=null
):IllegalArgumentException(message,cause){
    constructor(id:Long):this("Not found. id=$id")
}