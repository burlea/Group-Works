package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.layout_create_team.*

class NewTeamFragment(var teamsRef: CollectionReference, var user: DocumentReference) : Fragment() {

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
            val tasks = ArrayList<DocumentReference>()
            val members = ArrayList<DocumentReference>()
            val owners = ArrayList<DocumentReference>()
            owners.add(user)
            teamsRef.add(Team(teamName, tasks, members, owners))
        }
    }

}