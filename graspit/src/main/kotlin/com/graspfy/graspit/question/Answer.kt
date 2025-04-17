package com.graspfy.graspit.question

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
class Answer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long?=null,

    @Size(min = 1, max = 255)
    @NotNull
    var answer:String?="answer",

    var isCorrect:Boolean?=false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    var question:Question?=null,

)