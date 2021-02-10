package edu.rosehulman.burlea.groupworks

import kotlin.collections.ArrayList
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QuerySnapshot

data class Task (val name: String, val dueDate: String, var status: String,
                 var currentParticipants: Int, var maxParticipants: Int,
                 var minParticipants: Int,var description: String,
                 var requiredMaterials: String, var notesAndFiles: String,
                 var participants: ArrayList<String>){

    @get:Exclude
    var id = ""

    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Task {
            val task = snapshot.toObject(Task::class.java)!!
            task.id = snapshot.id
            return task
        }
    }
}