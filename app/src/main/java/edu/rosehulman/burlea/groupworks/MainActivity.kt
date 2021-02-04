package edu.rosehulman.burlea.groupworks

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, StartPage())
        ft.commit()
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
                    ft.replace(R.id.nav_host_fragment, TaskListFragment(teamSelected))
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
        ft.replace(R.id.nav_host_fragment, StartPage())
        ft.commit()
        return true
    }
}