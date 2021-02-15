package edu.rosehulman.burlea.groupworks

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.task_detail_view.*
import kotlinx.android.synthetic.main.task_detail_view.required_materials

class TaskDetailFragment : Fragment() {
    private lateinit var selectedTaskHandler: SelectedTaskHandler
    private lateinit var taskToDisplay: Task
    private lateinit var adapter: TaskAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedTaskHandler =
            (context as SelectedTaskHandler.SelectedTaskHandlerInterface).getSelectedTaskHandler()
        taskToDisplay = selectedTaskHandler.getSelectedTask()
        adapter =
            (context as AdapterHandler.AdapterHandlerInterface).getAdapterHandler().getAdapter()
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
        fillFields()
        setButtonListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setButtonListeners() {
        seeIfCanSignUp()
        seeIfCanComplete()

        sign_up_task.setOnClickListener() {
            adapter.updateParticipantsList(taskToDisplay)
            task_particpants_detail.text =
                "Participants: " + taskToDisplay.participantsList.size + 1 + "/" + taskToDisplay.maxParticipants
            participants_list.text = taskToDisplay.participantsList.toString()
            adapter.updateTaskToDisplay(taskToDisplay)
            seeIfCanSignUp()
        }

        complete_task.setOnClickListener() {
            taskToDisplay.status = "Completed"
            task_status_detail.text = taskToDisplay.status
            task_status_detail.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.completeStatus
                )
            )
            adapter.updateTaskToDisplay(taskToDisplay)
            seeIfCanComplete()
        }

        save_info.setOnClickListener() {
            taskToDisplay.description = description_text.text.toString()
            taskToDisplay.requiredMaterials = required_materials.text.toString()
            taskToDisplay.notesAndFiles = notes.text.toString()
            adapter.updateTaskToDisplay(taskToDisplay)
        }
    }

    private fun seeIfCanComplete() {
        val username = adapter.getUserFromId(userID)!!.username
        val isSignedUp = taskToDisplay.participantsList.contains(username)
        val taskIsNotCompleted = taskToDisplay.status != "Completed"
        complete_task.isEnabled = taskIsNotCompleted && isSignedUp
    }

    private fun seeIfCanSignUp() {
        val isAlreadySignedUp = adapter.seeIfSignedUp(taskToDisplay)
        val isTaskDone = taskToDisplay.status == "Completed"
        val taskIsAtCapacity = taskToDisplay.participantsList.size > taskToDisplay.maxParticipants
        sign_up_task.isEnabled = !(isAlreadySignedUp || isTaskDone || taskIsAtCapacity)
    }

    @SuppressLint("SetTextI18n")
    private fun fillFields() {
        task_name_detail.text = taskToDisplay.name
        task_due_date_detail.text = "Due " + taskToDisplay.dueDate
        task_status_detail.text = taskToDisplay.status

        if (taskToDisplay.status == "Completed") {
            task_status_detail.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.completeStatus
                )
            )
        } else {
            task_status_detail.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.incompleteStatus
                )
            )
        }

        task_minimum_detail.text = "Minimum: " + taskToDisplay.minParticipants
        task_particpants_detail.text =
            "Participants: " + taskToDisplay.participantsList.size + "/" + taskToDisplay.maxParticipants
        description_text.setText(taskToDisplay.description)
        required_materials.setText(taskToDisplay.requiredMaterials)
        notes.setText(taskToDisplay.notesAndFiles)

        val participantsList = taskToDisplay.participantsList
        if (participantsList.isNotEmpty()) {
            participants_list.text = participantsList.toString()
        }
    }
}