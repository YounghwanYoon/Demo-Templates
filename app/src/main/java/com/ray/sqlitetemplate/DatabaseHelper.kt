package com.ray.sqlitetemplate

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.webkit.CookieSyncManager.createInstance
import android.widget.Toast
import com.ray.sqlitetemplate.DatabaseHelper.FeedEntry.TABLE_NAME
import com.ray.sqlitetemplate.DatabaseHelper.FeedEntry.createTable
import com.ray.sqlitetemplate.DatabaseHelper.FeedEntry.deleteTable


//name variable is the DataBase name
class DatabaseHelper(var context: Context?, var name: String? = FeedEntry.DATABASE_NAME, factory: SQLiteDatabase.CursorFactory?, var version: Int) : SQLiteOpenHelper(context, name, null, 1) {

    object FeedEntry : BaseColumns {
        const val DATABASE_NAME: String = "mylist.db"
        internal const val TABLE_NAME: String = "mylist_data"
        internal const val UniquqID: String = "UniqueID"
        internal const val COL_ID: String = "ID"
        internal const val COL_PW: String = "PW"
        internal const val createTable: String = " CREATE TABLE " + TABLE_NAME + " (" +
                UniquqID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                COL_ID + " TEXT NOT NULL, " +
                COL_PW + " TEXT NOT NULL" +
                ");"
        internal const val deleteTable: String = "DROP TABLE IF EXISTS $createTable"
    }

    companion object singletonInstance{
        @SuppressLint("StaticFieldLeak")
        private var instance:DatabaseHelper? = null

        @Synchronized
        fun getInstance(context:Context){
            if(instance ==null)
                createInstance(context)
        }
    }

    @Synchronized
    private fun createInstance(context:Context): DatabaseHelper {
        if (instance == null) {
            instance = DatabaseHelper(context, name, null, version)
        }
        return instance!!
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(createTable)
    }

    // Version will be check and if it is not newer version, it will automatically call onUpgrade to update data.
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        p0?.execSQL(deleteTable)
        onCreate(p0)
    }

    fun insertData(userLoginData: LoginData) {
        // Gets the data repository in write mode
        val p0 = this.writableDatabase

        //First argument for insert() is simply the table name
        var cv = ContentValues()
        cv.put(FeedEntry.COL_ID, userLoginData.mLoginID)
        cv.put(FeedEntry.COL_PW, userLoginData.mLoginPW)

        var result = p0!!.insert(FeedEntry.TABLE_NAME, null, cv)
        //-1 means failed
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT)
        else
            Toast.makeText(context, "Succeeded", Toast.LENGTH_SHORT)
    }
    fun deleteData(uniqueID: String):Boolean{
        //Gets the data repository in write mode
        val p0 = this.writableDatabase

        //Define 'where' part of query
        val selection = FeedEntry.UniquqID + " LIKE ?"

        //Specify arguments in placeholder order
        val selectionArgs = arrayOf(uniqueID)

        val _success = p0!!.delete(FeedEntry.TABLE_NAME, selection, selectionArgs)

        return Integer.parseInt("$_success") != -1
    }
    fun getData(): Cursor {
        var database: SQLiteDatabase = this.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, FeedEntry.UniquqID, FeedEntry.COL_ID, FeedEntry.COL_PW)

       /* // Filter results WHERE "title" = 'My Title'
        val selection = "${FeedEntry.COL_ID} = ?"
        val selectionArgs = arrayOf("My Title")
*/
        val selection = null
        val selectionArgs = null
        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedEntry.UniquqID} DESC"

/*        val cursor = database.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        )*/
        val selectQuery = "SELECT * FROM "+ TABLE_NAME
        val cursor = database.rawQuery(selectQuery, selectionArgs)

        return cursor
    }
}




