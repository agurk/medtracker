package com.timothymoll.medtracker

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MedLogDataObject(mainAct: MainActivity) {

    private var dbData = emptyList<TakenMed>()
    private val dayData = HashMap<String, DataDay>()
    private val mainActivity : MainActivity = mainAct

    private val dateFormat  = DateTimeFormatter.ISO_LOCAL_DATE

    internal fun updateData(newData : List<TakenMed>) {
        dbData = newData
        dbData.forEach {
            val zDate = ZonedDateTime.parse(it.datetime)
            val date = zDate.format(dateFormat)
            val thisDay = dayData[date] ?: run {
                val newdd = DataDay(date)
                dayData[date] = newdd
                newdd
            }

            if (!thisDay.medsTaken.contains(it)) {
                thisDay.medsTaken.add(it)
                thisDay.details.add(DayMed(GUIStrings.detailTime(zDate), it.name, zDate))
                thisDay.total += it.amount
            }
        }
        mainActivity.updateGui(this)
    }

    fun todayTotal() : Int {
        val today = getToday()
        return dayData[today]?.total ?: 0
     }

    fun todayDetails() : List<DayMed> {
        val today = getToday()
        val result = dayData[today]?.details?.sortedByDescending { t -> t.time  } ?: emptyList()
        result.forEach {
            it.timeSince = GUIStrings.timeSince(it.date)
        }
        return result
    }

    fun historyData() : List<DataDay> {
        val today = getToday()
        val slice = dayData.filterNot { it.key ==  today}
        return slice.values.toList()
    }

    private fun getToday() : String {
        return ZonedDateTime.now().format(dateFormat)
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