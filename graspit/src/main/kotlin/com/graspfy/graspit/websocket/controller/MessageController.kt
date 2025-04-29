package com.graspfy.graspit.websocket.controller

import com.graspfy.graspit.websocket.Greeting
import com.graspfy.graspit.websocket.HelloMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody

@Controller
class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    fun greeting(@RequestBody message: HelloMessage):Greeting {
        Thread.sleep(1000)
        return Greeting("Hello , ${message.name}!")
    }

}