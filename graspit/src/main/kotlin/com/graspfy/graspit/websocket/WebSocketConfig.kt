package com.graspfy.graspit.websocket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Configuration
@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(quizWebSocketHandler(), "/quiz-session")
            .setAllowedOrigins("*")

    }
    @Bean
    fun quizWebSocketHandler(): WebSocketHandler {
        return QuizWebSocketHandler()
    }

}

class QuizWebSocketHandler : TextWebSocketHandler(){
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val rooms = ConcurrentHashMap<String, String>() //TODO{CREATE ROOM}

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
    }
    fun broadCast(roomId: String, message:String) {
        rooms[roomId]?.participants?.forEach{
            sessionID->sessions[sessionID]?.sendMessage(TextMessage(message))
        }
    }
}