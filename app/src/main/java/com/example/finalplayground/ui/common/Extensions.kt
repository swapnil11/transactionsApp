package com.example.finalplayground.ui.common

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import com.example.finalplayground.R
import com.example.finalplayground.ui.model.DateContentHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.absoluteValue

fun Fragment.showErrorBar(msg: String?) =
    view?.let {
        Snackbar.make(it, msg ?: getString(R.string.network_error), Snackbar.LENGTH_LONG).show()
    }

/**
 * Amount formatter extension which formats the amount in following manner.
 * For example:
 *
 * ```
 *  val positiveValue = "123".formattedAmount() // positiveValue is "$123.00"
 *  val negativeValue = "-123".formattedAmount() // negativeValue is "-$123.00"
 * ```
 */
fun String.formattedAmount(context: Context): String {

    val amount = toFloatOrNull() ?: 0f
    return if (amount > 0) {
        context.getString(R.string.positive_amount_format, amount.roundOff())
    } else {
        context.getString(R.string.negative_amount_format, amount.absoluteValue.roundOff())
    }
}

/**
 * Extension function to convert localDate to [SpannableStringBuilder] and [String] contentDescription for the field displayed.
 * So that this can be consumed directly into viewbinding.
 *
 * @param context Android [Context]
 * @param localDate optional param to be used for unit testing the logic.
 */
fun LocalDate.toDateContentHolder(context: Context, localDate: java.time.LocalDate = java.time.LocalDate.now()): DateContentHolder {
    val formatter = DateTimeFormatter.ofPattern("E d MMM")
    // It has observed that the content description reads out Sun as Literally sun rather than Sunday.
    // Hence modifying the content description with a formatter which will have complete content description
    val contentDescriptionFormatter = DateTimeFormatter.ofPattern("EEEE d MMM")
    val javaDate = toJavaLocalDate()

    val untilDays = javaDate.until(localDate, ChronoUnit.DAYS)
    val untilMonths = javaDate.until(localDate, ChronoUnit.MONTHS)
    val untilYears = javaDate.until(localDate, ChronoUnit.YEARS)

    val differenceValue = when {
        untilYears > 0 -> {
            context.getString(R.string.date_group_title, untilYears.toString(), ChronoUnit.YEARS)
        }
        untilMonths > 0 -> {
            context.getString(R.string.date_group_title, untilMonths.toString(), ChronoUnit.MONTHS)
        }
        untilDays > 0 -> {
            context.getString(R.string.date_group_title, untilDays.toString(), ChronoUnit.DAYS)
        }
        else -> {
            context.getString(R.string.today_label)
        }
    }

    val contentDescriptor = StringBuilder().apply {
        append(javaDate.format(contentDescriptionFormatter))
        append(" ")
        append(differenceValue)
    }.toString()

    return DateContentHolder(
        SpannableStringBuilder()
            .bold { append(javaDate.format(formatter)) }
            .append(" ")
            .append(differenceValue),
        contentDescriptor
    )
}

/**
 * Extension to roundOff Float values for example, Float value 123.33333 get converted to 123.33
 */
fun Float.roundOff(): String {
    return String.format(Locale.US, "%.2f", this)
}

/**
 * Formats the given string to Sentence case. Given a uppper case or lowercase string internally
 * the functions will return a string in Sentence case with first character as capitalised and
 * rest in lowercase.
 */
fun String.toSentenceCase(): String {
    return toLowerCase(Locale.US).capitalize(Locale.US)
}

/**
 * Given a string this function adds a single between each character.
 * To be used for reading out account number in talkback mode.
 */
fun String.toAccessibleNumber(): String {
    return replace(Regex("\\B"), " ")
}
