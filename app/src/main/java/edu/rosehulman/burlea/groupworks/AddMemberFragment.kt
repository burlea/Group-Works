package edu.rosehulman.burlea.groupworks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.add_members_layout.*

class AddMemberFragment(var team: Team?, var adapter: TaskAdapter) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var usernamesToAdd = ArrayList<User>()

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
            val user = User(username, null)
            usernamesToAdd.add(user)
            new_added_usernames_view.append("\n"+user.username)
            username_edit_text.setText("")
        }

        add_all_button.setOnClickListener {
            if(!usernamesToAdd.isEmpty()){
                for(user in usernamesToAdd){
                    adapter.getUsersRef().add(user)
                    adapter.getTeamRef().update("members", FieldValue.arrayUnion(user))
                    adapter.addUser(user)
                }
                usernamesToAdd.clear()
                new_added_usernames_view.text = ""
            }
            else{
                Toast.makeText(context, "No usernames have been supplied", Toast.LENGTH_LONG).show()
            }
        }
    }
}