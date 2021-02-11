package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_members_tasks_button_row.*
import kotlinx.android.synthetic.main.add_members_tasks_button_row.add_task_button
import kotlinx.android.synthetic.main.new_task_layout.*


class NewTaskFragment(var adapter: TaskAdapter) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_task_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_task_button.setOnClickListener {
            var taskName = task_name_edit_text.text.toString()
            var dueDate = due_date_edit_text.text.toString() + " @ " + due_time_edit_text.text.toString()
            var status = "Incomplete"
            var minParticipants = min_part_edit_text.text.toString().toInt()
            var maxParticipants = max_part_edit_text.text.toString().toInt()
            var currentParticipants = 0
            var description = description_edit_text.text.toString()
            var requiredMaterials = required_materials_edit_text.text.toString()
            var notesAndFiles = ""
            var participants = ArrayList<String>()
            adapter.add(Task(taskName, dueDate, status, currentParticipants, maxParticipants,
                            minParticipants, description, requiredMaterials, notesAndFiles, participants))
        }
    }
}