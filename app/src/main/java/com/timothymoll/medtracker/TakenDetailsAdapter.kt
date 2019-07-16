package com.timothymoll.medtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class TakenDetailsAdapter internal constructor(context: Context) : RecyclerView.Adapter<TakenDetailsAdapter.MTViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var times = emptyList<TakenMed>()

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
            holder.mtMedicineView.text = current.name
            holder.mtTimeView.text = ZonedDateTime.parse(current.datetime).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

        }

        internal fun setTimes(times: List<TakenMed>) {
            this.times = times
            notifyDataSetChanged()
        }

        override fun getItemCount() = times.size
    }
