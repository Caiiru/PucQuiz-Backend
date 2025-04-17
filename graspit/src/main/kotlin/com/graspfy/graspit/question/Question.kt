package com.graspfy.graspit.question

import com.fasterxml.jackson.annotation.JsonIgnore
import com.graspfy.graspit.Quiz.Quiz
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
class Question(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,


    @NotNull
    var question_text: String?=null,
    @NotNull
    var question_type: Int?=null,

    @NotNull
    var time_limit:Int=15,

    //var difficulty : Int = 1, // 1 - EASY , 2 - MEDIUM , 3 - HARD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="quiz_id", nullable = false)
    @JsonIgnore
    val quiz:Quiz?=null,

    @OneToMany(mappedBy = "question",cascade = [CascadeType.ALL], orphanRemoval = true)
    var answers: MutableSet<Answer>? = mutableSetOf(),
)