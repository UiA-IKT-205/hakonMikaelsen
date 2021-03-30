package com.example.angrypianonoises.data

data class Note(val value:String, val startScore:Long, val startNote:Long, val endNote:Long){

    var scoreTime = (startNote - startScore)
    var noteDuration = (endNote - startNote)

    override fun toString(): String {
        return "Note: $value, Time: $scoreTime ms, Note Duration: $noteDuration ms"
    }
}