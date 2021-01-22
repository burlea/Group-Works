package edu.rosehulman.burlea.groupworks

import kotlin.collections.ArrayList

data class Task (val name: String, val dueDate: String, var status: String,
                 var currentParticipants: Int, var maxParticipants: Int,
                 var minParticipants: Int,var description: String,
                 var requiredMaterials: String, var notesAndFiles: String,
                 var participants: ArrayList<String>)
