package edu.rosehulman.burlea.groupworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener{
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, LoginFragment())
            ft.commit()
        }

        signInButton.setOnClickListener{
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, SignInFragment())
            ft.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}