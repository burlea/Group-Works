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

    private var usernamesToAdd = ArrayList<String>()

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
//            val user = User(username, adapter.getTeamRef())
            usernamesToAdd.add(username)
            new_added_usernames_view.append("\n"+username)
            username_edit_text.setText("")
        }

        add_all_button.setOnClickListener {
            if(!usernamesToAdd.isEmpty()){
                for(username in usernamesToAdd){
                    val user = adapter.getUserFromUsername(username)
                    if(user != null){
                        val userDocRef = adapter.getUsersRef().document(user.id)
                        adapter.getTeamRef().update("members", FieldValue.arrayUnion(userDocRef))
                        team!!.addMember(userDocRef)
                    }
//                    adapter.getUsersRef().add(user!!)
                }
                usernamesToAdd.clear()
                new_added_usernames_view.text = ""
                fragmentManager!!.popBackStack()
            }
            else{
                Toast.makeText(context, "No usernames have been supplied", Toast.LENGTH_LONG).show()
            }
        }
    }
}