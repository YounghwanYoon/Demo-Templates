package com.ray.sqlitetemplate

import com.ray.sqlitetemplate.DatabaseManager.SingletonInstances.instance

class DatabaseManager {

    companion object  SingletonInstances {
        @Volatile private var instance: DatabaseManager? = null
        var mDataBaseHelper: DatabaseHelper? = null
        fun synchronized getInstance():DatabaseManager{
            if(instance == null)
                instance= DatabaseManager()
            return instance
        }
    }

    private fun synchronized initializeInstance() {
        if(SingletonInstances.instance == null){
            instance:DatabaseManager()
        }
    }


}