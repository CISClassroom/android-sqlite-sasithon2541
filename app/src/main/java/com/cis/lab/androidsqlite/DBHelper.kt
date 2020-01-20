package com.cis.lab.androidsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    val DATABASE_NAME = "todo.db"
    val DATABASE_VERSION = 1
    val TABLE_NAME = "text"
    val COLUMN_ID = "id"
    val COLUMN_TASKNAME = "taskname"

    //ADDDATABASE
    fun addTask(newTask: Task){
        val values = ContentValues()
        values.put(COLUMN_TASKNAME,newTask.taskname)

        val db = this.writableDatabase

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    //SHOWDATABASE
    fun getAlltask(): Cursor{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null)
    }

    //UPDATE
    fun updateTask(data: Task):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TASKNAME,data.taskname)
        val result = db.update(TABLE_NAME,contentValues,
            COLUMN_ID + " = " + data.id,null)
        close()
        return result
    }

    //DELETE
    fun deleteTask(id: Int):Int{
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME,
           COLUMN_ID + " = " + id,null)
        db.close()
        return result
    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " + COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_TASKNAME + " TEXT ) "
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val UPGRADE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(UPGRADE_TABLE)
        onCreate(db)
    }
}