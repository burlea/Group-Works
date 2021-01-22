package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_tasks.*

class TaskListFragment : Fragment() {
    private lateinit var adapter: TaskAdapter
    private lateinit var mainActivityContext: Context

    override fun onStart(){
        super.onStart()
        view_tasks.layoutManager = LinearLayoutManager(mainActivityContext)
        view_tasks.setHasFixedSize(true)
        view_tasks.adapter = adapter
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        mainActivityContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(mainActivityContext)
        adapter.initialize()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_tasks, container, false);
    }
}