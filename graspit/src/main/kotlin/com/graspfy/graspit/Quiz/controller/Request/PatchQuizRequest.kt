package com.graspfy.graspit.Quiz.controller.Request

import jakarta.validation.constraints.NotNull

data class PatchQuizRequest (
    @NotNull
    val title:String,
    //TODO -> CHANGE QUESTIONS
)