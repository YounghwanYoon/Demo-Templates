package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor

class DatabaseController(var context:Context) {
    private var db:DatabaseHelper = DatabaseHelper(context, DatabaseHelper.FeedEntry.DATABASE_NAME, null,1)

    companion object  SingletonInstances {
        @SuppressLint("StaticFieldLeak")
        private var instance: DatabaseController? = null
    }

    @Synchronized
    fun getInstance():DatabaseController{
        if(instance ==null){
            instance = DatabaseController(context)
        }
        return instance as DatabaseController
    }

    fun addData(loginData:LoginData){
        db.insertData(loginData)
        //db.close()
    }
    fun getData(): ArrayList<Pair<Long, LoginData>> {

        var readCursor = db.getData()
        var uniqueId = mutableListOf<Long>()
        //val userDatas = mutableListOf<LoginData>()

        val outPut= ArrayList<Pair<Long,LoginData>>()

        with(readCursor){
            while(moveToNext()){
                var uniqId = (readCursor.getString(0)).toLong()
                var userID = (readCursor.getString(1))
                var userPW = (readCursor.getString(2))

                outPut.add(Pair(uniqId, LoginData(userID,userPW)))
            }
        }
        readCursor.close()
        return outPut
    }

}