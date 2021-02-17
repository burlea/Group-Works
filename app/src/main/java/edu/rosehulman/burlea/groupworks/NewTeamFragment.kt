package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.layout_create_team.*

class NewTeamFragment(var teamsRef: CollectionReference, var user: DocumentReference) : Fragment() {

    private lateinit var selectedTaskHandler: SelectedTaskHandler
    private lateinit var adapter: TaskAdapter
    private lateinit var context: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter =
            (context as AdapterHandler.AdapterHandlerInterface).getAdapterHandler().getAdapter()
        this.context = (context as AppCompatActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_create_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        create_team_button.setOnClickListener {
            val teamName = new_team_edit_text.text.toString()
            val members = ArrayList<DocumentReference>()
            val owners = ArrayList<DocumentReference>()
            owners.add(user)
            members.add(user)
            val newTeam = Team(teamName, members, owners)
            val teamReference = teamsRef.add(newTeam)
            adapter.createListeners()
            val userObject = adapter.getUserFromId(userID)
            Team.fromReference(teamReference, userObject, teamsRef, this.context)
        }
    }

    fun goToTeamListView(userID: String, teamID: String) {
        Log.d(Constants.TAG, teamID)
        val taskListFragment: TaskListFragment = TaskListFragment.newInstance(userID, teamID, true)
        switchFragment(taskListFragment, "new Task")
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

}