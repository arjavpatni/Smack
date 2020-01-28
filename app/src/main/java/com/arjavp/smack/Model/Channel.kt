package com.arjavp.smack.Model

class Channel(val name: String, val description: String, val id: String) {
    //similar to ListView in CoderSwag
    override fun toString(): String {
        return "#$name"
    }
}