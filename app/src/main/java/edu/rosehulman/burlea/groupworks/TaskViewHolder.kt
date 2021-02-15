package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_detail_view.view.*
import kotlinx.android.synthetic.main.task_row_view.view.*


class TaskViewHolder: RecyclerView.ViewHolder {

    private val taskName: TextView = itemView.task_name
    private val taskDueDate: TextView = itemView.task_due_date
    private val taskStatus: TextView = itemView.task_status
    private val taskParticpantsNumber: TextView = itemView.task_participants
    private var selectedTask: Task? = null


    constructor(itemView: View, adapter: TaskAdapter, context: Context): super (itemView) {

        itemView.setOnClickListener {
            selectedTask = adapter.getSelectedTask(adapterPosition)
            val activity: AppCompatActivity = (context as AppCompatActivity)
            val taskHandler = (context as SelectedTaskHandler.SelectedTaskHandlerInterface).getSelectedTaskHandler()
            selectedTask?.let { it1 -> taskHandler.setSelectedTask(it1) }
            activity.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,TaskDetailFragment()).addToBackStack(null).commit()
        }
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
