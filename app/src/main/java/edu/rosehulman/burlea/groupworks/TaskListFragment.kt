package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_members_tasks_button_row.*
import kotlinx.android.synthetic.main.view_tasks.*
import kotlinx.android.synthetic.main.view_tasks.add_members_button
import kotlinx.android.synthetic.main.view_tasks.add_task_button
import kotlinx.android.synthetic.main.view_tasks.view.*

class TaskListFragment(teamSelected: String) : Fragment() {
    private lateinit var adapter: TaskAdapter
    private lateinit var mainActivityContext: Context

    override fun onStart(){
        super.onStart()
        view_tasks.recycler_view.layoutManager = LinearLayoutManager(mainActivityContext)
        view_tasks.recycler_view.setHasFixedSize(true)
        view_tasks.recycler_view.adapter = adapter
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        mainActivityContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(mainActivityContext)
        adapter.initialize()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_tasks, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_task_button.setOnClickListener{
            val newTaskFragment = NewTaskFragment()
            val ft = fragmentManager!!.beginTransaction()
            for(fragment in fragmentManager!!.fragments){
                ft.hide(fragment)
            }
            ft.add(R.id.nav_host_fragment, newTaskFragment)
            ft.addToBackStack("task")
            ft.commit()
        }
        add_members_button.setOnClickListener {
            val addMemberFragment = AddMemberFragment()
            val ft = fragmentManager!!.beginTransaction()
            for(fragment in fragmentManager!!.fragments){
                ft.hide(fragment)
            }
            ft.add(R.id.nav_host_fragment, addMemberFragment)
            ft.addToBackStack("member")
            ft.commit()
        }
    }
}