package edu.rosehulman.burlea.groupworks

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot

data class Team(
    val teamName: String = "",
    val tasks: ArrayList<DocumentReference> = ArrayList(),
){

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Team {
            val team = snapshot.toObject(Team::class.java)!!
            team.id = snapshot.id
            return team
        }
    }
}