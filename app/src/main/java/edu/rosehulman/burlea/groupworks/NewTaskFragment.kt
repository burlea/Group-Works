package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.add_members_tasks_button_row.add_task_button
import kotlinx.android.synthetic.main.new_task_layout.*


class NewTaskFragment(var adapter: TaskAdapter) : Fragment() {

    private var meridian: String = "AM"

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
        val timeSpinnerAdapter = ArrayAdapter.createFromResource(context!!, R.array.time_spinner_options, android.R.layout.simple_spinner_item)
        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        am_pm_spinner.adapter = timeSpinnerAdapter
        am_pm_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                meridian = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        add_task_button.setOnClickListener {
            val taskName = task_name_edit_text.text.toString()
            val dueDate = due_date_edit_text.text.toString()
            val dueTime = due_time_edit_text.text.toString() + " " + meridian
            val dueDateAndTime = dueDate + " @ " + dueTime
            val status = "Incomplete"
            val minParticipants: Int
            val maxParticipants: Int
            if(min_part_edit_text.text.isEmpty()){
                minParticipants = 1
            }
            else{
                minParticipants = min_part_edit_text.text.toString().toInt()
            }
            if(max_part_edit_text.text.isEmpty()){
                maxParticipants = 1
            }
            else{
                maxParticipants = max_part_edit_text.text.toString().toInt()
            }
            val currentParticipants = 0
            val description = description_edit_text.text.toString()
            val requiredMaterials = required_materials_edit_text.text.toString()
            val notesAndFiles = ""
            val participants = ArrayList<String>()
            val stringInputsList = listOfStringInputs(taskName, dueDate, dueTime, description, requiredMaterials)
            if(!allValidStringInputs(stringInputsList)){
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_LONG).show()
            }
            else if(!allValidParticipantRequirements(minParticipants, maxParticipants)){
                Toast.makeText(context, "Invalid participation requirements", Toast.LENGTH_LONG).show()
            }
            else {
                val teamRef = adapter.getTeamRef()
                adapter.add(Task(taskName, dueDateAndTime, status, currentParticipants, maxParticipants,
                        minParticipants, description, requiredMaterials, notesAndFiles, participants,teamRef))
                fragmentManager!!.popBackStack()
            }
        }
    }

    private fun listOfStringInputs(taskName: String, dueDate: String, dueTime: String, description: String, requiredMaterials: String): ArrayList<String> {
        var stringInputList = ArrayList<String>()
        stringInputList.add(taskName)
        stringInputList.add(dueDate)
        stringInputList.add(dueTime)
        stringInputList.add(description)
        stringInputList.add(requiredMaterials)
        return stringInputList
    }

    private fun allValidStringInputs(stringInputs: ArrayList<String>): Boolean{
        for(input in stringInputs){
            if(input.isEmpty()){
                return false
            }
        }
        return true
    }

    private fun allValidParticipantRequirements(minPart: Int, maxPart: Int): Boolean{
        if(minPart < 1 || maxPart < minPart){
            return false
        }
        return true
    }
}