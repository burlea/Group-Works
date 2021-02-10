package edu.rosehulman.burlea.groupworks

data class User(
    val username: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val teamsOwned: ArrayList<Team> = ArrayList(),
    val teamsMemberOf: ArrayList<Team> = ArrayList()
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