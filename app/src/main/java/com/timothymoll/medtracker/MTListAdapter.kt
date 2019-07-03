package com.timothymoll.medtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medtracker.R

class MTListAdapter internal constructor(context: Context) : RecyclerView.Adapter<MTListAdapter.MTViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var times = emptyList<MedTaken>() // Cached copy of words

        inner class MTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mtMedicineView: TextView = itemView.findViewById(R.id.medicine_textview)
            val mtTimeView: TextView = itemView.findViewById(R.id.time_textview)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MTViewHolder {
            val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
            return MTViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MTViewHolder, position: Int) {
            val current = times[position]
            holder.mtMedicineView.text = current.medicine
            holder.mtTimeView.text = current.time
        }

        internal fun setTimes(times: List<MedTaken>) {
            this.times = times
            notifyDataSetChanged()
        }

        override fun getItemCount() = times.size
    }
