package com.graspfy.graspit.Quiz

import com.fasterxml.jackson.annotation.JsonIgnore
import com.graspfy.graspit.User.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(name="quiz")
class Quiz(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    var id:Long = 0,

    @NotNull
    @Column(name="title")
    var title:String = "title",

    var createdAt: Instant = Instant.now(),
    @NotNull
    var type:Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by")
    @JsonIgnore
    var createdBy: User? = null,
    )