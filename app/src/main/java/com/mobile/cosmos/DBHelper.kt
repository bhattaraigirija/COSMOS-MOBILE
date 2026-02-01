package com.mobile.cosmos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION){
        companion object{
            private const val DATABASE_NAME = "cosmos_db"
            private const val DATABASE_VERSION = 1

            private  const val TABLE_USER = "users"
            private  const val COL_ID = "id"
            private  const val COL_NAME = "name"
            private  const val COL_ADDRESS = "address"
            private  const val COL_DOB = "dob"
            private  const val COL_EMAIL = "email"
            private  const val COL_PHONE = "phone"
            private  const val COL_PASSWORD = "password"
            private  const val COL_GENDER = "gender"
            private  const val COL_COURSE = "course"

        }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_USER(
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NAME TEXT,
            $COL_ADDRESS TEXT,
            $COL_DOB TEXT,
            $COL_EMAIL TEXT,
            $COL_PHONE TEXT,
            $COL_PASSWORD TEXT,
            $COL_GENDER TEXT,
            $COL_COURSE TEXT
            )
        """.trimIndent()

        db?.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVerion: Int,
        newVerion: Int
    ){
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun insertUser(
        name:String,
        address: String,
        dob:String,
        email:String,
        phone:String,
        password:String,
        gender:String,
        course:String,
    ): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME,name)
        values.put(COL_ADDRESS,address)
        values.put(COL_DOB,dob)
        values.put(COL_EMAIL,email)
        values.put(COL_PHONE,phone)
        values.put(COL_PASSWORD,password)
        values.put(COL_GENDER,gender)
        values.put(COL_COURSE, course)

        val result = db.insert(TABLE_USER, null, values)
        db.close()
         return result !=-1L
    }

    fun getAllUsers(): ArrayList<Users> {
        val list = ArrayList<Users>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER ORDER BY $COL_ID DESC", null)


        if (cursor.moveToFirst()) {
            do {
                val user = Users(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                    dob = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOB)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)),
                    course = cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE))
                )
                list.add(user)
            } while (cursor.moveToNext())
        }


        cursor.close()
        db.close()
        return list
    }


    // ---------------- GET BY ID ----------------
    fun getUserById(userId: Int): Users? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER WHERE $COL_ID=?", arrayOf(userId.toString()))


        var user: Users? = null
        if (cursor.moveToFirst()) {
            user = Users(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                dob = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOB)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)),
                gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER)),
                course = cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE))
            )
        }


        cursor.close()
        db.close()
        return user
    }


    // ---------------- UPDATE ----------------
    fun updateUser(user: Users): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, user.name)
            put(COL_ADDRESS, user.address)
            put(COL_DOB, user.dob)
            put(COL_EMAIL, user.email)
            put(COL_PHONE, user.phone)
            put(COL_PASSWORD, user.password)
            put(COL_GENDER, user.gender)
            put(COL_COURSE, user.course)
        }


        val rows = db.update(TABLE_USER, values, "$COL_ID=?", arrayOf(user.id.toString()))
        db.close()
        return rows > 0
    }


    // ---------------- DELETE ----------------
    fun deleteUser(userId: Int): Boolean {
        val db = writableDatabase
        val rows = db.delete(TABLE_USER, "$COL_ID=?", arrayOf(userId.toString()))
        db.close()
        return rows > 0
    }

}