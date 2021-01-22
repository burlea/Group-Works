package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class TaskAdapter(var context: Context) : RecyclerView.Adapter<TaskViewHolder>() {
    private val taskItems = ArrayList<Task>()

    fun initialize(){

    }

    override fun getItemCount() = taskItems.size

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_row_view, parent, false)
        return TaskViewHolder(view, this)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, index: Int) {
        viewHolder.bind(taskItems[index])
    }

}