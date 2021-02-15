package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.add_members_tasks_button_row.*
import kotlinx.android.synthetic.main.add_members_tasks_button_row.add_task_button
import kotlinx.android.synthetic.main.new_task_layout.*

class NoTeamsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.no_teams_view, container, false)
    }
}