package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import kotlin.random.Random

class TaskAdapter(var context: Context, var userID: String) : RecyclerView.Adapter<TaskViewHolder>() {
    private val taskItems = ArrayList<Task>()


    fun initialize(){
        val list = arrayListOf<String>()

        val task: Task = Task("GroupWorks",
            "01/22/2021 @ 10:00am", "Incomplete",
        2,2,2,
            "create an Android App",
            "Kotlin, Guts, and no sleep",
            "Who needs notes", list)

        taskItems.add(task)
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

    fun add(task: Task){
        taskItems.add(0, task)
        notifyItemInserted(0)
    }

}