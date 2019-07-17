package com.timothymoll.medtracker

import android.app.Activity
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MedLogDataObject {

    private var dbData = emptyList<TakenMed>()
    private val dayData = HashMap<String, DataDay>()
    private val mainActivity : MainActivity

    constructor (mainAct : MainActivity) {
        mainActivity = mainAct
    }

    internal fun updateData(newData : List<TakenMed>) {
        dbData = newData
        dbData.forEach {
            val zDate = ZonedDateTime.parse(it.datetime)
            val date = zDate.format(DateTimeFormatter.BASIC_ISO_DATE)
            val prettyDate = zDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val time = zDate.format(DateTimeFormatter.ofPattern("HH:mm"))
            val thisDay = dayData[date] ?: run {
                val newdd = DataDay(date)
                dayData[date] = newdd
                newdd
            }

            if (!thisDay.medsTaken.contains(it)) {
                thisDay.medsTaken.add(it)
                thisDay.details.add(DayMed(time, it.name, zDate))
                thisDay.total += it.amount
            }
        }
        mainActivity.updateGui(this)
    }

    fun todayTotal() : Int {
        val today = ZonedDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        return dayData[today]?.total ?: 0
     }

    fun todayDetails() : List<DayMed> {
        val today = ZonedDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        val result = dayData[today]?.details?.sortedByDescending { t -> t.time  } ?: emptyList()
        result.forEach {
            val duration = Duration.between(it.date, ZonedDateTime.now())
            val mins = duration.toMinutes()
            val hours = duration.minusMinutes(mins).toHours()
            it.timeSince = ""
            if (hours > 0) it.timeSince = hours.toString() + "h "
            it.timeSince += mins.toString()
            if (hours == 0L) it.timeSince += " mins"
        }
        return result
    }

    fun historyData() : List<DataDay> {
        val today = ZonedDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        val slice = dayData.filterNot { it.key ==  today}
        return slice.values.toList()
    }
}

data class DataDay (
    val date : String,
    var total : Int = 0,
    val medsTaken : MutableList<TakenMed> = mutableListOf(),
    val details : MutableList<DayMed> = mutableListOf()
)

data class DayMed (
    val time : String,
    val name : String,
    val date : ZonedDateTime,
    var timeSince : String = ""
)