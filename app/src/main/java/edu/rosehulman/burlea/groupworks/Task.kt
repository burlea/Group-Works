package edu.rosehulman.burlea.groupworks

import java.util.*
import kotlin.collections.ArrayList

data class Task (val name: String, val dueDate: Date, var status: String,
                 var currentParticipants: Int, var maxParticipants: Int,
                 var minParticipants: Int,var description: String,
                 var requiredMaterials: String, var notesAndFiles: String,
                 var participants: ArrayList<String>)
