package edu.rosehulman.burlea.groupworks

class SelectedTaskHandler {

    private lateinit var selectedTask: Task

    fun getSelectedTask(): Task {
        return selectedTask
    }

    fun setSelectedTask(task: Task) {
        selectedTask = task
    }

    interface SelectedTaskHandlerInterface {
        fun getSelectedTaskHandler(): SelectedTaskHandler
    }
}