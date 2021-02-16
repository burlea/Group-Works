package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.add_members_layout.*

class AddMemberFragment(var team: Team?, var teamsRef: CollectionReference) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var usernamesToAdd: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_members_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_username_button.setOnClickListener {
            val username = username_edit_text.text.toString()
            val user = User(username, teamsRef.document(team!!.id))
            usernamesToAdd?.add(user)
            new_added_usernames_view.append("\n"+user.username)
        }
    }
}