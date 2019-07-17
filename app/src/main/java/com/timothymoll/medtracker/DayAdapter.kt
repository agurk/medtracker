package com.timothymoll.medtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DayAdapter internal constructor(context: Context) : RecyclerView.Adapter<DayAdapter.MTViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var days = emptyList<DataDay>()

    inner class MTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val totalAmount : TextView = itemView.findViewById(R.id.day_amount)
            val dayDetails : RecyclerView = itemView.findViewById(R.id.day_details)
            val dayDate : TextView = itemView.findViewById(R.id.day_date)
            val detailsButton : ToggleButton = itemView.findViewById(R.id.details_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MTViewHolder {
            val itemView = inflater.inflate(R.layout.day_item, parent, false)

            val detailsView = itemView.findViewById<RecyclerView>(R.id.day_details)
            val detailsAdapter = DayDetailsAdapter(itemView.context)
            detailsView.adapter = detailsAdapter
            detailsView.layoutManager = LinearLayoutManager(itemView.context)

            return MTViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MTViewHolder, position: Int) {
            val current = days[position]
            holder.totalAmount.text = current.total.toString()
            holder.dayDate.text = current.date
            val dayDetailsAdapter = holder.dayDetails.adapter as DayDetailsAdapter
            dayDetailsAdapter.setTimes(current.details)

            holder.detailsButton.setOnClickListener  {
                if (holder.detailsButton.isChecked) {
                    holder.dayDetails.visibility = View.VISIBLE
                } else {
                    holder.dayDetails.visibility = View.GONE
                }
            }
        }

        internal fun setTimes(days: List<DataDay>) {
            this.days = days
            notifyDataSetChanged()
        }

        override fun getItemCount() = days.size
    }
