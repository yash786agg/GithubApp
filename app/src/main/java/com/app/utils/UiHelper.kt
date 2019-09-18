package com.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.app.githubapp.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UiHelper(private val context : Context)
{
    fun getConnectivityStatus() : Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showSnackBar(view: View, content: String) = Snackbar.make(view, content, Snackbar.LENGTH_LONG).show()

    /**
     * Return the update Repos Time after converting into a Readable Format
     * @param [updatedAt] takes as a Input variable
     * @return the time in String format
     */

    fun getProjectUpdatedTime(updatedAt : String) : String {

        var formattedTime = ""
        val initialDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val resultantDateFormat = "dd MMM yyyy, HH:mm"
        val formatFrom = SimpleDateFormat(initialDateFormat, Locale.getDefault())
        formatFrom.isLenient = false
        val formatTo = SimpleDateFormat(resultantDateFormat, Locale.getDefault())
        formatTo.isLenient = false
        val date: Date?
        try
        {
            date = formatFrom.parse(updatedAt)
            formattedTime = getTimeDiff(formatTo.format(date!!))
        }
        catch (e: ParseException)
        {
            e.printStackTrace()
        }

        return formattedTime
    }

    /**
     * @return the Current Date Time
     */

    private fun getCurrentDataTime() : String
    {
        val date = Date()
        val strDateFormat = "dd MMM yyyy, HH:mm"
        val dateFormat = SimpleDateFormat(strDateFormat, Locale.US)
        return dateFormat.format(date)
    }

    /**
     * This function is calculate the Time Difference.
     * @param [serverTime] takes as a Input variable
     * @return the time in String format
     */

    private fun getTimeDiff(serverTime : String) : String {

        val format = "dd MMM yyyy, HH:mm"

        val sdf = SimpleDateFormat(format, Locale.getDefault())

        val dateObjServer : Date? = sdf.parse(serverTime)
        val dateObjCurrent : Date? = sdf.parse(getCurrentDataTime())

        val diff = dateObjCurrent!!.time - dateObjServer!!.time

        val diffDays = (diff / (24 * 60 * 60 * 1000)).toInt()

        if(diffDays != 0 && diffDays >= 2)
            return diffDays.toString() + " " + context.resources.getString(R.string.days)
        else if(diffDays != 0 && diffDays == 1)
            return diffDays.toString() + " " + context.resources.getString(R.string.day)

        val diffHours = (diff / (60 * 60 * 1000)).toInt()

        if(diffHours != 0 && diffHours >= 2)
            return diffHours.toString() + " " + context.resources.getString(R.string.hours)
        else if(diffHours != 0 && diffHours == 1)
            return diffHours.toString() + " " + context.resources.getString(R.string.hour)

        val diffMin = (diff / (60 * 1000)).toInt()

        return if(diffMin >= 60) {
            val min : Int = diffMin/60
            min.toString() + " " + context.resources.getString(R.string.hours)
        } else
            diffMin.toString() + " " + context.resources.getString(R.string.min)
    }
}