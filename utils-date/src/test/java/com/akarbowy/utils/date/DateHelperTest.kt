package com.akarbowy.utils.date

import org.junit.Test


class DateHelperTest {

    @Test
    fun `parses year`() {
        val date = "2006-02-06T08:00:00Z"

        val year = DateHelper.parseYear(date)

        assert(year == 2006)
    }

    @Test
    fun `returns null in case of an error`() {
        val date = "2005-02-06"

        val year = DateHelper.parseYear(date)

        assert(year == null)
    }

}