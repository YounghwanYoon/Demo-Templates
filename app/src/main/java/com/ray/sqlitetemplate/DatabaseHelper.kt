package com.ray.sqlitetemplate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//name variable is the DataBase name
class DatabaseHelper(var context: Context?, var name: String? = DATABASE_NAME, factory: SQLiteDatabase.CursorFactory?, var version: Int) : SQLiteOpenHelper(context, name, null, 1) {

    companion object {
        const val DATABASE_NAME:String ="mylist.db"
        private const val TABLE_NAME:String = "mylist_data"
        private const val UniquqID:String = "UniqueID"
        private const val COL_ID:String = "ID"
        private const val COL_PW:String ="PW"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable:String = " CREATE TABLE " + TABLE_NAME + " (" +
                UniquqID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                COL_ID + " TEXT NOT NULL, " +
                COL_PW + " TEXT NOT NULL"+
                ");"
        p0!!.execSQL(createTable)
    }

    // Version will be check and if it is not newer version, it will automatically call onUpgrade to update data.
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(userLoginData: LoginData){
        val p0 = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_ID, userLoginData.mLoginID)
        cv.put(COL_PW, userLoginData.mLoginPW)

        var result = p0!!.insert(TABLE_NAME, null, cv)
        //-1 means failed
        if(result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT)
        else
            Toast.makeText(context, "Succeeded",Toast.LENGTH_SHORT)
    }





}