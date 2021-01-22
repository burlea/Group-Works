package edu.rosehulman.burlea.groupworks

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_row_view.view.*


class TaskViewHolder: RecyclerView.ViewHolder {

    private val taskName: TextView = itemView.task_name
    private val taskDueDate: TextView = itemView.task_due_date
    private val taskStatus: TextView = itemView.task_status
    private val taskParticpantsNumber: TextView = itemView.task_participants


    constructor(itemView: View, adapter: TaskAdapter): super (itemView) {

    }

    fun bind(task: Task) {
        taskName.text = task.name
        taskDueDate.text = task.dueDate
        taskStatus.text = task.status
        taskParticpantsNumber.text = getParticipantsString(task)
    }

    private fun getParticipantsString(task: Task): String {
        return "Participants: " + task.currentParticipants.toString() +
                "/" + task.maxParticipants.toString()
    }
}
