package edu.rosehulman.burlea.groupworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val fragment = AddMembersOrTaskFragment()
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.start_page, fragment)
            ft.commit()
        }
    }
}