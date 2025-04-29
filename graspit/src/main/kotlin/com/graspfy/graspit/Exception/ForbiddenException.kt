package com.graspfy.graspit.Exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ForbiddenException (
    message:String = "Forbidden",
    cause: Throwable?=null
):IllegalArgumentException(message,cause)
