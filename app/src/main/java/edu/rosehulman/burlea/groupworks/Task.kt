package edu.rosehulman.burlea.groupworks

import com.google.firebase.firestore.DocumentReference
import kotlin.collections.ArrayList
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class Task(
    val name: String = "", val dueDate: String = "", var status: String = "",
    var currentParticipants: Int = 0, var maxParticipants: Int = 0,
    var minParticipants: Int = 0, var description: String = "",
    var requiredMaterials: String = "", var notesAndFiles: String = "",
    var participantsList: List<String> = ArrayList()){

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Task {
            val task = snapshot.toObject(Task::class.java)!!
            task.participantsList = snapshot.get("participants") as List<String>
            task.id = snapshot.id
            return task
        }
    }
}