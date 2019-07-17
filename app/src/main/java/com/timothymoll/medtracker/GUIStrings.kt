package com.timothymoll.medtracker

import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class GUIStrings {
    companion object Methods {

        private const val zeroDurationString : String = "Just now"

        fun dayDetails(dayMed: DayMed): String {
            var result = dayMed.name + " at " + dayMed.time
            if (dayMed.timeSince != "") {
                result += "  ("
                result += dayMed.timeSince
                if (dayMed.timeSince != zeroDurationString) result += " ago"
                result += ")"
            }
            return result
        }

        fun timeSince(date : ZonedDateTime) : String {
            val duration = Duration.between(date, ZonedDateTime.now())
            val hours = duration.toHours()
            val mins = duration.minusHours(hours).toMinutes()
            var timeSince = ""
            if ( hours + mins == 0L) return zeroDurationString
            if (hours > 0) timeSince = hours.toString() + "h "
            timeSince += mins.toString()
            if (hours == 0L) {
                timeSince += " min"
                if (mins > 0) timeSince += "s"
            }
            return timeSince
        }

        fun detailTime(date: ZonedDateTime) : String {
            return date.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

    }
}