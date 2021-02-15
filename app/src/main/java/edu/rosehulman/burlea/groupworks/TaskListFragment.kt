package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.view_tasks.*
import kotlinx.android.synthetic.main.view_tasks.add_members_button
import kotlinx.android.synthetic.main.view_tasks.add_task_button
import kotlinx.android.synthetic.main.view_tasks.view.*

class TaskListFragment() : Fragment() {
    private lateinit var adapter: TaskAdapter
    private lateinit var mainActivityContext: Context
    private var uid: String? = null
    private var team: Team? = null
    private val teamRef = FirebaseFirestore.getInstance().collection("teams")
    private var isOwner = false
    private lateinit var adapterHandler: AdapterHandler
    private var hasTeam = false;

    override fun onStart() {
        super.onStart()
        view_tasks.recycler_view.layoutManager = LinearLayoutManager(mainActivityContext)
        view_tasks.recycler_view.setHasFixedSize(true)
        view_tasks.recycler_view.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityContext = context
        adapterHandler = (context as AdapterHandler.AdapterHandlerInterface).getAdapterHandler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            uid = it.getString("UID")
            val teamId = it.getString("TeamId")
            hasTeam = it.getBoolean("hasTeam")
            if (hasTeam){
                getTeam(teamId!!)
            }else{
                val noTeamFragment: Fragment = NoTeamsFragment()
                switchFragment(noTeamFragment, "noTeam")
            }
        }
        adapter = uid?.let { TaskAdapter(mainActivityContext, it) }!!
        adapterHandler.setAdapter(adapter)
    }

    private fun getTeam(teamId: String) {
        teamRef.document(teamId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                team = Team.fromSnapshot(documentSnapshot)
                adapter.setCurrentTeam(team)
                seeIfOwner()
                title.text = team!!.teamName
                title.visibility = View.VISIBLE
            }.addOnFailureListener { e ->
                Log.e(Constants.TAG, "could not get team:FAILURE", e)
            }
    }

    private fun seeIfOwner() {
        val listOfOwners = team!!.owners
        for (ownerRef in listOfOwners) {
            if (ownerRef.id == userID) {
                isOwner = true
            }
        }
        if (isOwner) {
            showOwnerButtons()
        } else {
            hideOwnerButtons()
        }
    }

    private fun showOwnerButtons() {
        add_task_button.visibility = View.VISIBLE
        add_members_button.visibility = View.VISIBLE
    }

    private fun hideOwnerButtons() {
        add_task_button.visibility = View.INVISIBLE
        add_members_button.visibility = View.INVISIBLE
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
        add_task_button.setOnClickListener {
            val newTaskFragment = NewTaskFragment(adapter)
            switchFragment(newTaskFragment, "task")
        }
        add_members_button.setOnClickListener {
            val addMemberFragment = AddMemberFragment()
            switchFragment(addMemberFragment, "member")
        }
        add_task_button.visibility = View.GONE
        add_members_button.visibility = View.GONE
    }

    private fun switchFragment(fragment: Fragment, name: String) {
        val ft = fragmentManager!!.beginTransaction()
        for (fragment in fragmentManager!!.fragments) {
            ft.hide(fragment)
        }
        ft.add(R.id.nav_host_fragment, fragment)
        ft.addToBackStack(name)
        ft.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(uid: String, teamID: String, hasTeam: Boolean) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putString("UID", uid)
                    putString("TeamId", teamID)
                    putBoolean("hasTeam", hasTeam)
                }
            }
    }
}