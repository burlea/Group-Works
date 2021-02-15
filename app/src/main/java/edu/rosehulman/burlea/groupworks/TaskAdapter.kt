package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import java.time.LocalDate
import kotlin.random.Random

class TaskAdapter(var context: Context, var userID: String) :
    RecyclerView.Adapter<TaskViewHolder>() {
    private val taskItems = ArrayList<Task>()
    private var taskToDisplayPosition = 0
    private var teamRef = FirebaseFirestore.getInstance().collection("teams")
    private var tasksRef = FirebaseFirestore.getInstance().collection("tasks")
    private lateinit var currentTeam: Team
    private var listener: ListenerRegistration? = null

    private fun createListener() {
        clearData()
        val teamRefDocument = teamRef.document(currentTeam.id)
        listener = tasksRef.whereEqualTo("team", teamRefDocument)
            .addSnapshotListener { snapshot: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        val task = Task.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                taskItems.add(0, task)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val position = taskItems.indexOfFirst { task.id == it.id }
                                taskItems[position] = task
                                notifyItemChanged(position)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val position = taskItems.indexOfFirst { task.id == it.id }
                                taskItems.remove(task)
                                notifyItemRemoved(position)
                            }
                        }
                    }
                }
            }
    }
    private fun clearData() {
        taskItems.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = taskItems.size

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_row_view, parent, false)
        return TaskViewHolder(view, this, context)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, index: Int) {
        viewHolder.bind(taskItems[index])
    }


    fun getSelectedTask(adapterPosition: Int): Task? {
        return taskItems[adapterPosition]
    }

    fun add(task: Task) {
        tasksRef.add(task)
    }

    fun updateTaskToDisplay(taskToDisplay: Task) {
        taskItems[taskToDisplayPosition] = taskToDisplay
        Log.d(Constants.TAG, taskItems[taskToDisplayPosition].id)
        tasksRef.document(taskItems[taskToDisplayPosition].id).set(taskItems[taskToDisplayPosition])
        notifyItemChanged(taskToDisplayPosition)
    }

    fun setTaskToDisplay(taskToDisplayPosition: Int) {
        this.taskToDisplayPosition = taskToDisplayPosition
    }

    fun setCurrentTeam(team: Team?) {
        currentTeam = team!!
        createListener()
    }

    fun getTeamRef(): DocumentReference {
        return teamRef.document(currentTeam.id)
    }
}