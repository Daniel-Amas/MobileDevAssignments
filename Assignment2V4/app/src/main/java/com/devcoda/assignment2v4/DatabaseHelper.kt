package com.devcoda.assignment2v4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.BufferedReader
import java.io.InputStreamReader

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_LOCATIONS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_ADDRESS TEXT, "
                + "$COLUMN_LATITUDE REAL, "
                + "$COLUMN_LONGITUDE REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        onCreate(db)
    }

    fun insertLocation(address: String, latitude: Double, longitude: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ADDRESS, address)
        values.put(COLUMN_LATITUDE, latitude)
        values.put(COLUMN_LONGITUDE, longitude)

        val result = db.insert(TABLE_LOCATIONS, null, values)
        db.close()
        return result
    }

    fun updateLocation(id: Int, address: String, latitude: Double, longitude: Double): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ADDRESS, address)
        values.put(COLUMN_LATITUDE, latitude)
        values.put(COLUMN_LONGITUDE, longitude)

        val result = db.update(TABLE_LOCATIONS, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun deleteLocation(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_LOCATIONS, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun getAllLocations(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_LOCATIONS", null)
    }

    fun searchLocation(address: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_LOCATIONS WHERE $COLUMN_ADDRESS LIKE ?", arrayOf("%$address%"))
    }

    fun populateDatabaseFromCSV(context: Context) {
        val inputStream = context.resources.openRawResource(R.raw.locations)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use { bufferedReader ->
            bufferedReader.forEachLine { line ->
                val columns = line.split(",")
                if (columns.size == 3) {
                    val address = columns[0].trim()
                    val latitude = columns[1].trim().toDoubleOrNull()
                    val longitude = columns[2].trim().toDoubleOrNull()

                    if (latitude != null && longitude != null) {
                        insertLocation(address, latitude, longitude)
                    }
                }
            }
        }
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "locationsDB"
        const val TABLE_LOCATIONS = "locations"
        const val COLUMN_ID = "id"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }
}