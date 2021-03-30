package com.example.angrypianonoises.data

data class Note(val value:String, val start:Long, val end:Long){

    var noteDuration = (end - start)

    override fun toString(): String {
        return "$value, $noteDuration ms"
    }
}