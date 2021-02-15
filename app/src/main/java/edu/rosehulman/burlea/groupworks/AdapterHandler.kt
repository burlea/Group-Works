package edu.rosehulman.burlea.groupworks

class AdapterHandler{
    private lateinit var adapter: TaskAdapter

    fun getAdapter(): TaskAdapter {
        return adapter
    }

    fun setAdapter(adapter: TaskAdapter){
        this.adapter = adapter
    }

    interface  AdapterHandlerInterface {
        fun getAdapterHandler(): AdapterHandler
    }
}
