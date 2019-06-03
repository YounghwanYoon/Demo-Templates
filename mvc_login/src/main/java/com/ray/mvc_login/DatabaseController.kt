package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ray.sqlitetemplate.repository.model.UserData


class DatabaseController(var context:Context) {
    private var db:DatabaseHelper = DatabaseHelper(context, DatabaseHelper.FeedEntry.DATABASE_NAME, null,1)
    val Tag ="DatabaseController.kt"
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

    fun updateUserPW(uniqueID: String, userNewPW: String){
        db.updateData(uniqueID, userNewPW)
    }

    fun addData(loginData: UserData){
        db.insertData(loginData)
        //db.close()
    }
    fun getData(): ArrayList<Pair<Long, UserData>> {

        var readCursor = db.getData()
        var uniqueId = mutableListOf<Long>()
        //val userDatas = mutableListOf<UserData>()

        val outPut= ArrayList<Pair<Long, UserData>>()

        with(readCursor){
            while(moveToNext()){
                var uniqId = (readCursor.getString(0)).toLong()
                var userID = (readCursor.getString(1))
                var userPW = (readCursor.getString(2))

                outPut.add(Pair(uniqId, UserData(userID, userPW)))
            }
        }
        readCursor.close()
        return outPut
    }
    fun removeData(uniqueID:String){
        Log.i(Tag, "Have you deleted the selected data ? ${db.deleteData(uniqueID)}" )
        db.close()
    }


}