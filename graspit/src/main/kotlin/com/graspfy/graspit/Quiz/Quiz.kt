package com.graspfy.graspit.Quiz

import com.fasterxml.jackson.annotation.JsonIgnore
import com.graspfy.graspit.question.Question
import com.graspfy.graspit.User.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name="Quiz")
class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    var id:Long = 0,

    @NotNull
    @Column(name="title")
    var title:String = "title",

    @Column(name="created_at", updatable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),

    @OneToMany(mappedBy = "id", cascade = [(CascadeType.ALL)])
    var questions:MutableSet<Question>? = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    var createdBy: User? = null,
    ){
    fun setCreator(user:User){
        this.createdBy = user
        user.quizzes?.add(this);
    }
}