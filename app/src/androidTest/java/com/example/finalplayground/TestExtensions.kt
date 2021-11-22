package com.example.finalplayground

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.finalplayground.ui.common.formattedAmount
import com.example.finalplayground.ui.common.toAccessibleNumber
import com.example.finalplayground.ui.common.toDateContentHolder
import kotlinx.datetime.LocalDate
import org.junit.Assert
import org.junit.Test

class TestExtensions {

    @Test
    fun testFormatter() {
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        Assert.assertEquals("123".formattedAmount(targetContext), "$123.00")
        Assert.assertEquals("-123.199999".formattedAmount(targetContext), "-$123.20")
        Assert.assertEquals("123.199999".formattedAmount(targetContext), "$123.20")
    }

    @Test
    fun testAccessibleAccountNumber() {
        Assert.assertEquals("12345".toAccessibleNumber(), "1 2 3 4 5")
    }

    @Test
    fun testLocalDateExtension() {
        val currentDate = java.time.LocalDate.parse("2021-11-16")
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        var localDate = LocalDate.parse("2018-10-10")
        var dateHolder = localDate.toDateContentHolder(targetContext, currentDate)

        Assert.assertEquals(dateHolder.spannableStringBuilder.toString(), "Wed 10 Oct 3 Years ago")
        Assert.assertEquals(dateHolder.contentDescription, "Wednesday 10 Oct 3 Years ago")

        localDate = LocalDate.parse("2021-11-10")
        dateHolder = localDate.toDateContentHolder(targetContext, currentDate)

        Assert.assertEquals(dateHolder.spannableStringBuilder.toString(), "Wed 10 Nov 6 Days ago")
        Assert.assertEquals(dateHolder.contentDescription, "Wednesday 10 Nov 6 Days ago")

        localDate = LocalDate.parse("2021-11-16")
        dateHolder = localDate.toDateContentHolder(targetContext, currentDate)

        Assert.assertEquals(dateHolder.spannableStringBuilder.toString(), "Tue 16 Nov Today")
        Assert.assertEquals(dateHolder.contentDescription, "Tuesday 16 Nov Today")
    }
}
