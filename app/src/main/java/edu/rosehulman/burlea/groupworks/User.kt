package edu.rosehulman.burlea.groupworks

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class User(
    val username: String = "",
    var teamLastViewedByUser: DocumentReference? = null) {

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): User {
            val user = snapshot.toObject(User::class.java)!!
            user.id = snapshot.id
            user.teamLastViewedByUser = snapshot.get("teamLastViewed") as DocumentReference?
            return user
        }
    }
}