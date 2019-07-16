package com.timothymoll.medtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CurrentAdapter internal constructor(context: Context) : RecyclerView.Adapter<CurrentAdapter.MTViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var times = emptyList<TakenMed>()

        inner class MTViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val valu: TextView = itemView.findViewById(R.id.medicine_textview)
            val label: TextView = itemView.findViewById(R.id.time_textview)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MTViewHolder {
            val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
            return MTViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MTViewHolder, position: Int) {
            holder.label.text = "Total Amount"
            var total = 0
            times.forEach {
                total += it.amount
            }
            holder.valu.text = total.toString()
        }

        internal fun setTimes(times: List<TakenMed>) {
            this.times = times
            notifyDataSetChanged()
        }

        override fun getItemCount() = 1
    }
