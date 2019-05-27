package com.akarbowy.utils.date

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


private const val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

object DateHelper {

    fun parseYear(dateText: String, format: String = dateTimePattern): Int? {
        val date = try {
            SimpleDateFormat(format).parse(dateText)
        } catch (e: ParseException) {
            null
        }

        return date?.let {
            with(Calendar.getInstance()) {
                time = date
                get(Calendar.YEAR)
            }
        }
    }

}
