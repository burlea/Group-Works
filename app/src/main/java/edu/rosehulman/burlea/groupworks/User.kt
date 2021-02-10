package edu.rosehulman.burlea.groupworks

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot

data class User(
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val teamsOwned: ArrayList<Team> = ArrayList(),
    val teamsMemberOf: ArrayList<Team> = ArrayList(),
    val teamLastViewedId: String = ""
) {

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): User {
            val user = snapshot.toObject(User::class.java)!!
            user.id = snapshot.id
            return user
        }
    }
}