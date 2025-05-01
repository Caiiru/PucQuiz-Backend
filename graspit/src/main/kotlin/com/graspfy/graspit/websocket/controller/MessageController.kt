package com.graspfy.graspit.websocket.controller

import com.graspfy.graspit.websocket.Greeting
import com.graspfy.graspit.websocket.HelloMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.util.HtmlUtils

@Controller
class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    fun greeting(message: HelloMessage):Greeting {
        Thread.sleep(1000)
        return Greeting("Hello , ${message.name?.let { HtmlUtils.htmlEscape(it) }}!")
    }

}