package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.burlea.groupworks.AddMembersOrTaskFragment.Companion.newInstance
import kotlinx.android.synthetic.main.add_members_tasks_button_row.*
import kotlinx.android.synthetic.main.add_members_tasks_button_row.add_members_button
import kotlinx.android.synthetic.main.add_members_tasks_button_row.add_task_button
import kotlinx.android.synthetic.main.add_members_tasks_layout.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddMembersOrTaskFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_members_tasks_layout, container, false)
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMembersOrTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}