package com.timothymoll.medtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DayDetailsAdapter internal constructor(context: Context) : RecyclerView.Adapter<DayDetailsAdapter.MTViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var times = emptyList<DayMed>()

        inner class MTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val detailsTV: TextView = itemView.findViewById(R.id.details_textview)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MTViewHolder {
            val itemView = inflater.inflate(R.layout.day_details_item, parent, false)
            return MTViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MTViewHolder, position: Int) {
            val current = times[position]
            holder.detailsTV.text = GUIStrings.dayDetails(current)
        }

        internal fun setTimes(times: List<DayMed>) {
            this.times = times.sortedBy { it.time }.reversed()
            notifyDataSetChanged()
        }

        override fun getItemCount() = times.size
    }
