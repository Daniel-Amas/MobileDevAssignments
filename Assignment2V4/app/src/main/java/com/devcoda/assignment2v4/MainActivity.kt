package com.devcoda.assignment2v4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Locations>
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var showAllBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var addNewBtn: Button
    private lateinit var adapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        if (isDatabaseEmpty()) {
            dbHelper.populateDatabaseFromCSV(this)
            Toast.makeText(this, "Database populated from CSV", Toast.LENGTH_SHORT).show()
        }

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        adapter = LocationAdapter(newArrayList,
            onDeleteClick = { location -> deleteLocation(location) },
            onUpdateClick = { location -> updateLocation(location) }
        )
        newRecyclerView.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchBar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchLocation(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchLocation(it)
                }
                return true
            }
        })

        showAllBtn = findViewById(R.id.showAllBtn)
        resetBtn = findViewById(R.id.resetBtn)
        addNewBtn = findViewById(R.id.addNewBtn)

        showAllBtn.setOnClickListener {
            showAllLocations()
        }

        resetBtn.setOnClickListener {
            resetRecyclerView()
        }

        addNewBtn.setOnClickListener {
            showAddLocationDialog()
        }
    }

    private fun searchLocation(query: String) {
        newArrayList.clear() // Clear the current list

        // Search for locations matching the query
        val cursor = dbHelper.searchLocation(query)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LONGITUDE))
                newArrayList.add(Locations(id, address, latitude, longitude)) // Add each result to the list
            } while (cursor.moveToNext())
        }
        cursor.close()
        adapter.notifyDataSetChanged() // Notify adapter that data has changed
    }

    private fun isDatabaseEmpty(): Boolean {
        val cursor = dbHelper.getAllLocations()
        val isEmpty = !cursor.moveToFirst() // True if no rows exist
        cursor.close()
        return isEmpty
    }

    private fun showAllLocations() {
        newArrayList.clear()
        val cursor = dbHelper.getAllLocations()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LONGITUDE))
                newArrayList.add(Locations( id, address, latitude, longitude))
            } while (cursor.moveToNext())
        }
        cursor.close()
        adapter.notifyDataSetChanged() // Notify adapter that data has changed
    }

    private fun resetRecyclerView() {
        newArrayList.clear()
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "List reset", Toast.LENGTH_SHORT).show()
    }

    private fun showAddLocationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Location")

        val layout = layoutInflater.inflate(R.layout.dialog_add_location, null)
        builder.setView(layout)

        val addressInput = layout.findViewById<EditText>(R.id.addressInput)
        val latitudeInput = layout.findViewById<EditText>(R.id.latitudeInput)
        val longitudeInput = layout.findViewById<EditText>(R.id.longitudeInput)

        builder.setPositiveButton("Add") { dialog, _ ->
            val address = addressInput.text.toString()
            val latitude = latitudeInput.text.toString().toDoubleOrNull()
            val longitude = longitudeInput.text.toString().toDoubleOrNull()

            if (address.isNotBlank() && latitude != null && longitude != null) {
                dbHelper.insertLocation(address, latitude, longitude)
                Toast.makeText(this, "Location added", Toast.LENGTH_SHORT).show()
                showAllLocations()
            } else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun deleteLocation(location: Locations) {
        dbHelper.deleteLocation(location.id)
        newArrayList.remove(location)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show()
    }

    private fun updateLocation(location: Locations) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Location")

        val layout = layoutInflater.inflate(R.layout.dialog_add_location, null)
        builder.setView(layout)

        val addressInput = layout.findViewById<EditText>(R.id.addressInput)
        val latitudeInput = layout.findViewById<EditText>(R.id.latitudeInput)
        val longitudeInput = layout.findViewById<EditText>(R.id.longitudeInput)

        // Pre-fill inputs with existing location data
        addressInput.setText(location.address)
        latitudeInput.setText(location.latitude.toString())
        longitudeInput.setText(location.longitude.toString())

        builder.setPositiveButton("Update") { dialog, _ ->
            val address = addressInput.text.toString()
            val latitude = latitudeInput.text.toString().toDoubleOrNull()
            val longitude = longitudeInput.text.toString().toDoubleOrNull()

            if (address.isNotBlank() && latitude != null && longitude != null) {
                dbHelper.updateLocation(location.id, address, latitude, longitude)
                Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show()
                showAllLocations()
            } else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}
