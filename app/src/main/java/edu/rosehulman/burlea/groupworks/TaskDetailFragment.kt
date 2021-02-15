package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.new_task_layout.*
import kotlinx.android.synthetic.main.task_detail_view.*
import kotlinx.android.synthetic.main.task_detail_view.required_materials

class TaskDetailFragment : Fragment() {
    private lateinit var selectedTaskHandler: SelectedTaskHandler
    private lateinit var taskToDisplay: Task
    private lateinit var adapter: TaskAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedTaskHandler = (context as SelectedTaskHandler.SelectedTaskHandlerInterface).getSelectedTaskHandler()
        taskToDisplay = selectedTaskHandler.getSelectedTask()
        adapter = (context as AdapterHandler.AdapterHandlerInterface).getAdapterHandler().getAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_detail_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        description_text.setText(taskToDisplay.description)
        required_materials.setText(taskToDisplay.requiredMaterials)
        notes.setText(taskToDisplay.notesAndFiles)
        val participantsList = taskToDisplay.participants
        if (participantsList.size != 0){
            participants_list.text = participantsList.toString()
        }

    }
}