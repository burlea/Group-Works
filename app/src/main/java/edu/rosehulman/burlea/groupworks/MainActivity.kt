package edu.rosehulman.burlea.groupworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore;

private val RC_SIGN_IN = 1
val auth = FirebaseAuth.getInstance()
lateinit var authListener: FirebaseAuth.AuthStateListener
lateinit var userID: String
lateinit var taskView: TaskListFragment
private val usersRef = FirebaseFirestore.getInstance().collection("users")
private val teamsRef = FirebaseFirestore.getInstance().collection("teams")
private var teamsUserIsIn = hashMapOf<String, Team>()
private var taskHandler = SelectedTaskHandler()
var adapterHandler = AdapterHandler()
private var isSignedIn = false

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginButtonPressedListener,
    SelectedTaskHandler.SelectedTaskHandlerInterface, AdapterHandler.AdapterHandlerInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeListeners()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }

    private fun initializeListeners() {
        authListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            if (user != null) {
                userID = user.uid
                isSignedIn = true
                findLastViewedTeam()
            } else {
                isSignedIn = false
                switchToLoginFragment()
            }
        }
    }

    private fun switchToTaskViewFragment(teamID: String, hasTeam: Boolean) {
        val ft = supportFragmentManager.beginTransaction()
        taskView = TaskListFragment.newInstance(userID, teamID, hasTeam)
        ft.replace(R.id.nav_host_fragment, taskView)
        ft.commit()
    }

    private fun findLastViewedTeam() {
        var userLastTeamID = "No Last Viewed Team"
        usersRef.document(userID)
            .get()
            .addOnSuccessListener { document ->
                val teamLastViewedValue = document.get("teamLastViewedByUser")
                if (teamLastViewedValue == null) {
                    switchToTaskViewFragment(userLastTeamID, false)
                } else {
                    val teamLastViewedReference =
                        document.get("teamLastViewedByUser") as DocumentReference
                    userLastTeamID = teamLastViewedReference.id
                    switchToTaskViewFragment(userLastTeamID, true)
                }
            }
    }

    private fun switchToLoginFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, LoginFragment())
        ft.commit()
    }

    override fun onLoginButtonPressed() {
        launchLoginUI()
    }

    private fun launchLoginUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val loginIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        startActivityForResult(
            loginIntent,
            RC_SIGN_IN
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> signOut()
            R.id.view_teams -> showTeams()
            R.id.new_team -> seeIfAddTeam()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun seeIfAddTeam(): Boolean{
        if (!isSignedIn) {
            presentCantAddTeamsToast()
        } else {
            addTeam()
        }
        return true
    }

    private fun presentCantAddTeamsToast(){
        Toast.makeText(
            this,
            "Please log in to add a team",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showTeams(): Boolean {
        if (!isSignedIn) {
            presentCantSeeTeamsToast()
        } else {
            getTeams()
        }
        return true
    }

    private fun presentCantSeeTeamsToast() {
        Toast.makeText(
            this,
            "Please log in to view your teams",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getTeams(): Boolean {
        val teamNames = ArrayList<String>()
        teamsRef.whereArrayContains("members", usersRef.document(userID))
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val team = Team.fromSnapshot(document)
                    val teamName = team.teamName
                    teamsUserIsIn[teamName] = team
                    teamNames.add(teamName)
                }
                showTeamsDialog(teamNames)
            }.addOnFailureListener {
                Log.d(Constants.TAG, "Error: $it")
            }
        return true
    }

    private fun showTeamsDialog(usersTeams: ArrayList<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a team to view")
        builder.setItems(
            usersTeams.toTypedArray()
        ) { _, teamIndex ->
            val teamSelected = usersTeams[teamIndex]
            val ft = supportFragmentManager.beginTransaction()
            val team = teamsUserIsIn.get(teamSelected)
            val taskListFragment = TaskListFragment.newInstance(userID, team!!.id, true)
            ft.replace(R.id.nav_host_fragment, taskListFragment)
            ft.commit()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun addTeam(): Boolean{
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, NewTeamFragment(teamsRef, usersRef.document(userID))).addToBackStack("add team")
        ft.commit()
        return true
    }

    private fun signOut(): Boolean {
        if (!isSignedIn) {
            presentCantSeeSignOutToast()
        } else {
            signOutLoggedInUser()
        }
        return true
    }

    private fun presentCantSeeSignOutToast() {
        Toast.makeText(
            this,
            "Please log in to sign out",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun signOutLoggedInUser() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, LoginFragment())
        ft.commit()
        val adapter = getAdapterHandler().getAdapter()
        adapter.setLastViewedTeam()
       isSignedIn = false
    }

    override fun getSelectedTaskHandler() = taskHandler

    override fun getAdapterHandler() = adapterHandler
}