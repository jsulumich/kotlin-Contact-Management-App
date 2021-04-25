package com.project4.contactmanagementapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.project4.contactmanagementapp.database.Message
import com.project4.contactmanagementapp.module.contact.ContactBean

class ContactDatabaseAdapter(context: Context){
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ContactDatabase"
        private val TABLE_NAME = "ContactTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private  val KEY_PHONE = "phone"
    }
    private val mContext = context
    private val databaseHelper = DatabaseHelper(mContext)
    private val contactList= ArrayList<ContactBean>()

    fun insertData(name:String,email: String,phone : String): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL,email)
        contentValues.put(KEY_PHONE, phone)
        val id = db.insert(TABLE_NAME,null,contentValues)
        return id
    }

    fun updateData(name: String,email: String,phone : String,id: Int): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL,email)
        contentValues.put(KEY_PHONE, phone)
        val WhereArgs = arrayOf<String>(id.toString())
        val updatedRows = db.update(TABLE_NAME,contentValues, KEY_ID+" =?" ,WhereArgs)
        return updatedRows
    }

    fun getAllData(): ArrayList<ContactBean> {
        contactList.clear()
        val db =  databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID,KEY_NAME, KEY_EMAIL, KEY_PHONE)
        val cursor: Cursor = db.query(TABLE_NAME,columns,null,null,null,null,null)
        while (cursor.moveToNext()){
            val contactBean = ContactBean()
            val index1 =  cursor.getColumnIndex(KEY_NAME)
            val index2 =  cursor.getColumnIndex(KEY_EMAIL)
            val index3 =  cursor.getColumnIndex(KEY_PHONE)
            val index4 =  cursor.getColumnIndex(KEY_ID)
            contactBean.name = cursor.getString(index1)
            contactBean.email  = cursor.getString(index2)
            contactBean.phone = cursor.getString(index3)
            contactBean.id = cursor.getInt(index4)
            contactList.add(contactBean)
        }
        return contactList
    }

    class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

        private  val mContext = context
        override fun onCreate(db: SQLiteDatabase?) {
            val querry = "CREATE TABLE "+TABLE_NAME+" ("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" VARCHAR(255), "+ KEY_EMAIL+" TEXT, "+ KEY_PHONE+" TEXT);"
            db?.execSQL(querry)
            if(db!=null){
                Message.message(mContext,"database created successfully" )
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
            onCreate(db)
        }

    }

}