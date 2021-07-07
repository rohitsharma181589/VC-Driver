package com.vehiclecare.vc_driver_arvind.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.ItemTripsBinding
import com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide.TripDatum
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TripAdapter(var tripsData: ArrayList<TripDatum>) :
    RecyclerView.Adapter<TripAdapter.MyViewHolder>(), Filterable {

    val allTripData: ArrayList<TripDatum> = tripsData

    class MyViewHolder(var itemTripsBinding: ItemTripsBinding) :
        RecyclerView.ViewHolder(itemTripsBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_trips, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tripData = tripsData[position]
        holder.itemTripsBinding.tripData = tripData


        if (tripData.startDate != null) {
            val utcTimeZone = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            utcTimeZone.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = utcTimeZone.parse(tripData.startDate)
            val targetFormat = SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
            targetFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            val formattedDate: String = targetFormat.format(date)
            holder.itemTripsBinding.tvStartDate.text = "Start Date: " + formattedDate
        }

        if (tripData.endDate != null) {
            val utcTimeZone = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            utcTimeZone.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = utcTimeZone.parse(tripData.endDate)
            val targetFormat = SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
            targetFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            val formattedDate: String = targetFormat.format(date)
            holder.itemTripsBinding.tvEndDate.text = "End Date: " + formattedDate
        } else holder.itemTripsBinding.tvEndDate.text = "End Date: "

    }

    override fun getItemCount(): Int {
        return tripsData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                tripsData = if (charString.isEmpty()) {
                    allTripData
                } else {
                    val filteredList: ArrayList<TripDatum> = ArrayList()
                    for (row in allTripData) {
                        if (row.licensePlateNumber.lowercase(Locale.getDefault()).contains(
                                charString.lowercase(Locale.getDefault())
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = tripsData
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                tripsData = filterResults.values as ArrayList<TripDatum>
                notifyDataSetChanged()
            }
        }
    }
}