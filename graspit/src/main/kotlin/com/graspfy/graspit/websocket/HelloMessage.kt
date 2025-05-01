package com.graspfy.graspit.websocket

class HelloMessage(name: String?=null) {

    var name : String? = null

    fun GetName() : String = name?:"John Doe"

    fun SetName(newName: String) {
        this.name = newName
    }

}