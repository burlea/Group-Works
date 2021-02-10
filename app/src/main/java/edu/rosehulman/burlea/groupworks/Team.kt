package edu.rosehulman.burlea.groupworks

data class Team(
    val teamName: String = "",
    val tasks: ArrayList<Task> = ArrayList(),
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