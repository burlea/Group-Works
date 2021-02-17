package edu.rosehulman.burlea.groupworks

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class TaskAdapter(var context: Context, var userID: String) :
    RecyclerView.Adapter<TaskViewHolder>() {
    private val taskItems = ArrayList<Task>()
    private val users = ArrayList<User>()
    private var taskToDisplayPosition = 0
    private var teamRef = FirebaseFirestore.getInstance().collection("teams")
    private var tasksRef = FirebaseFirestore.getInstance().collection("tasks")
    private var usersRef = FirebaseFirestore.getInstance().collection("users")
    private var currentTeam: Team? = null
    private var taskListener: ListenerRegistration? = null
    private var userListener: ListenerRegistration? = null
    private var lastRemovedTask: Task? = null

    fun createListeners() {
        clearData()
        if (currentTeam !=null){
            createTaskListener()
        }
        createUserListener()
    }

     fun createUserListener() {
        userListener =
            usersRef.addSnapshotListener { snapshot: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        val user = User.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                users.add(0, user)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val position = users.indexOfFirst { user.id == it.id }
                                users[position] = user
                            }
                            DocumentChange.Type.REMOVED -> {
                                users.remove(user)
                            }
                        }
                    }
                }
            }
    }

    private fun createTaskListener() {
        clearData()
        val teamRefDocument = teamRef.document(currentTeam!!.id)
        taskListener = tasksRef.whereEqualTo("team", teamRefDocument)
            .addSnapshotListener { snapshot: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        val task = Task.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                taskItems.add(0, task)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val position = taskItems.indexOfFirst { task.id == it.id }
                                taskItems[position] = task
                                notifyItemChanged(position)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val position = taskItems.indexOfFirst { task.id == it.id }
                                taskItems.remove(task)
                                notifyItemRemoved(position)
                            }
                        }
                    }
                }
            }
    }

    private fun clearData() {
        taskItems.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = taskItems.size

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_row_view, parent, false)
        return TaskViewHolder(view, this, context)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, index: Int) {
        viewHolder.bind(taskItems[index])
    }

    fun getSelectedTask(adapterPosition: Int): Task {
        return taskItems[adapterPosition]
    }

    fun add(task: Task) {
        tasksRef.add(task)
    }

    fun updateTaskToDisplay(taskToDisplay: Task) {
        taskItems[taskToDisplayPosition] = taskToDisplay
        tasksRef.document(taskItems[taskToDisplayPosition].id).set(taskItems[taskToDisplayPosition])
        notifyItemChanged(taskToDisplayPosition)
    }

    fun setTaskToDisplay(taskToDisplayPosition: Int) {
        this.taskToDisplayPosition = taskToDisplayPosition
    }

    fun setCurrentTeam(team: Team?) {
        currentTeam = team!!
        createTaskListener()
    }

    fun getTeamRef(): DocumentReference {
        return teamRef.document(currentTeam!!.id)
    }

    fun getUsersRef(): CollectionReference{
        return usersRef
    }

    fun setLastViewedTeam() {
        val currentUser = getUserFromId(userID)
        Log.d(Constants.TAG,"Current user " + currentUser.toString())
        Log.d(Constants.TAG,"current user team last viewed " + currentUser!!.teamLastViewedByUser.toString())
        Log.d(Constants.TAG,"current user team " + currentTeam.toString())
        if (currentTeam !=null){
            currentUser.teamLastViewedByUser = teamRef.document(currentTeam!!.id)
        }
        usersRef.document(userID).set(currentUser)
    }

    fun seeIfSignedUp(task: Task): Boolean {
        val currentUser = getUserFromId(userID)
        return task.participantsList.contains(currentUser!!.username)
    }

    fun getUserFromId(userId: String): User? {
        return users.find { user ->
            user.id == userId
        }
    }

    fun addUser(user: User){
        users.add(user)
    }

    fun getUserFromUsername(username: String): User?{
        return users.find { user ->
            user.username == username
        }
    }

    fun updateParticipantsList(task: Task) {
        val username = getUserFromId(userID)!!.username
        task.participantsList.add(username)
        task.currentParticipants++
        notifyDataSetChanged()
    }

    fun removeTask(adapterPosition: Int) {
        val taskToRemove = taskItems[adapterPosition]
        tasksRef.document(taskItems[adapterPosition].id).delete()
        notifyItemRemoved(adapterPosition)
        saveTask(taskToRemove)
    }

    private fun saveTask(taskToRemove: Task) {
        lastRemovedTask = taskToRemove
    }

    fun retrieveOldTask() {
        add(lastRemovedTask!!)
        lastRemovedTask = null
    }

    fun getTeam(newTeam: Team) {

    }

    fun addNewUser(newUser: User) {
        usersRef.add(newUser)
    }


}