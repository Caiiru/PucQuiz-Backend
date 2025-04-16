package com.graspfy.graspit.User

import com.fasterxml.jackson.annotation.JsonIgnore
import com.graspfy.graspit.Quiz.Quiz
import com.graspfy.graspit.Role.Role
import jakarta.persistence.Entity
import jakarta.persistence.*
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CurrentTimestamp
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity
class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="user_seq", allocationSize = 1)
    var id: Long? = null,

    @Size(max = 50)
    @NotNull
    var name:String = "username",

    @NotNull
    @Column(unique = true)
    val email:String="email@email.com",
    val password:String="password",

    @OneToMany(mappedBy = "createdBy", cascade = [CascadeType.ALL],orphanRemoval = true)
    var quizzes: MutableSet<Quiz>? = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name="user_role",
        joinColumns = [JoinColumn(name="id_user")],
        inverseJoinColumns = [JoinColumn(name="id_role")]
    )
    @JsonIgnore
    val roles:MutableSet<Role> = mutableSetOf()
)