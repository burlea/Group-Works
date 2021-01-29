package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.fragment_login.*


class StartPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val activity: AppCompatActivity = (context as AppCompatActivity)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, LoginFragment()).commit()
        }

        signInButton.setOnClickListener {
            val activity: AppCompatActivity = (context as AppCompatActivity)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, SignInFragment()).commit()
        }
    }
}
