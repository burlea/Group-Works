package edu.rosehulman.burlea.groupworks

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class Team(
    val teamName: String = "",
    val tasks: ArrayList<DocumentReference> = ArrayList(),
    val members: ArrayList<DocumentReference> = ArrayList(),
    val owners: ArrayList<DocumentReference> = ArrayList(),
    ){

    @get:Exclude
    var id = ""

    fun addMember(user: DocumentReference){
        members.add(user)
    }

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Team {
            val team = snapshot.toObject(Team::class.java)!!
            team.id = snapshot.id
            return team
        }

        fun fromReference(
            teamReference: Task<DocumentReference>,
            userObject: User?,
            teamsRef: CollectionReference,
            context: AppCompatActivity
        ) {
           teamReference.addOnSuccessListener {
               userObject!!.teamLastViewedByUser = teamsRef.document(it.id)
               goToTeamListView(userID, it.id, context)
           }
        }

        private fun goToTeamListView(userID: String, id: String, activity: AppCompatActivity) {
            val taskListFragment: TaskListFragment = TaskListFragment.newInstance(userID, id, true)
            activity.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,taskListFragment).addToBackStack(null).commit()
        }
    }
}