package com.devcoda.assignment2v4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    private val locationsList: ArrayList<Locations>,
    private val onDeleteClick: (Locations) -> Unit,
    private val onUpdateClick: (Locations) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationsList[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = locationsList.size

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressText: TextView = itemView.findViewById(R.id.addressText)
        private val latText: TextView = itemView.findViewById(R.id.latText)
        private val longText: TextView = itemView.findViewById(R.id.longText)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val updateButton: Button = itemView.findViewById(R.id.updateButton)

        fun bind(location: Locations) {
            addressText.text = location.address
            latText.text = "Latitude: ${location.latitude}"
            longText.text = "Longitude: ${location.longitude}"

            deleteButton.setOnClickListener { onDeleteClick(location) }
            updateButton.setOnClickListener { onUpdateClick(location) }
        }
    }
}
