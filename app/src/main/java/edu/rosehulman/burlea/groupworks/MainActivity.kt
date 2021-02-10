package edu.rosehulman.burlea.groupworks

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore;


private val RC_SIGN_IN = 1
val auth = FirebaseAuth.getInstance()
lateinit var authListener: FirebaseAuth.AuthStateListener
lateinit var userID: String
lateinit var taskView: TaskListFragment

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginButtonPressedListener {
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
                switchToTaskViewFragment(userID)
            } else {
                switchToLoginFragment()
            }
        }
    }

    private fun switchToTaskViewFragment(uid: String) {
        val ft = supportFragmentManager.beginTransaction()
        taskView = TaskListFragment.newInstance(uid)
        ft.replace(R.id.nav_host_fragment, taskView)
        ft.commit()
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTeams(): Boolean {
        showTeamsDialog()
        return true
    }

    private fun showTeamsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an animal")
        val usersTeams = getTeams()
        builder.setItems(usersTeams,
                DialogInterface.OnClickListener { dialog, teamIndex ->
                    val teamSelected = usersTeams[teamIndex]
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.nav_host_fragment, TaskListFragment())
                    ft.commit()
                })
        val dialog = builder.create()
        dialog.show()
    }

    private fun getTeams(): Array<String> {
        return arrayOf("Boston Bruners", "Jwooty fan club", "Smite Squad")
    }

    private fun signOut(): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, LoginFragment())
        ft.commit()
        return true
    }
}