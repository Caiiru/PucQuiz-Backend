package com.graspfy.graspit.websocket

import com.graspfy.graspit.Quiz.Quiz
import com.graspfy.graspit.User.User
import com.graspfy.graspit.question.Answer
import com.graspfy.graspit.question.Question
import org.springframework.web.socket.WebSocketSession
import java.util.Timer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class Room (
    val roomId:String,
    val hostSessionId:Long,
    val roomCode:String,
    val maxPlayers:Int = 99,
    var currentPlayers:Int = 0,
    var questions:List<Question>,
    var questionCounter:AtomicInteger
)
{
    val participants = ConcurrentHashMap<String,PlayerInfo>()

    var roomState:RoomState = RoomState.WAITING

    var currentQuiz:Quiz? = null

    var timer:Timer? = null



    fun addPlayer(session: WebSocketSession, playerName: String,playerId:Long, isHost:Boolean = false):Boolean{
        if(participants.size >= maxPlayers) return false

        participants[session.id] = PlayerInfo(
            session = session,
            playerId = playerId,
            score = 0,
            isHost = isHost,
            lastAnswer = null,
            asnwerTime = 0,
            playerName = playerName
        )
        return true
    }

    fun removePlayer(sessionId:String){
        participants.remove(sessionId)

    }

    fun startQuiz(quiz:Quiz){
        this.currentQuiz = quiz
        this.roomState = RoomState.IN_PROGRESS
        this.questionCounter.set(0)
        questions = quiz.questions!!.toList()

        resetAllScores()
    }
    private fun resetAllScores() {
        participants.values.forEach {it.score = 0}
    }
    fun nextQuestion():Question?{
        return questions[questionCounter.getAndIncrement()];
    }

    data class PlayerInfo(
        val session: WebSocketSession,
        val playerId:Long,
        val playerName:String,
        var score:Int,
        val isHost:Boolean,
        var lastAnswer: Int?,
        var asnwerTime: Long?

    )
}