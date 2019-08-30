package com.app.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.app.nandroid.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UiHelper @Inject constructor(private val application: Application)
{
    fun getConnectivityStatus() : Boolean
    {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showSnackBar(view: View, content: String) = Snackbar.make(view, content, Snackbar.LENGTH_LONG).show()

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

    private fun getCurrentDataTime() : String
    {
        val date = Date()
        val strDateFormat = "dd MMM yyyy, HH:mm"
        val dateFormat = SimpleDateFormat(strDateFormat, Locale.US)
        return dateFormat.format(date)
    }

    private fun getTimeDiff(serverTime: String) : String {

        val format = "dd MMM yyyy, HH:mm"

        val sdf = SimpleDateFormat(format, Locale.getDefault())

        val dateObjServer : Date? = sdf.parse(serverTime)
        val dateObjCurrent : Date? = sdf.parse(getCurrentDataTime())

        val diff = dateObjCurrent!!.time - dateObjServer!!.time

        val diffDays = (diff / (24 * 60 * 60 * 1000)).toInt()

        if(diffDays != 0 && diffDays >= 2)
            return diffDays.toString() + " " + application.resources.getString(R.string.days)
        else if(diffDays != 0 && diffDays == 1)
            return diffDays.toString() + " " + application.resources.getString(R.string.day)

        val diffHours = (diff / (60 * 60 * 1000)).toInt()

        if(diffHours != 0 && diffHours >= 2)
            return diffHours.toString() + " " + application.resources.getString(R.string.hours)
        else if(diffHours != 0 && diffHours == 1)
            return diffHours.toString() + " " + application.resources.getString(R.string.hour)

        val diffMin = (diff / (60 * 1000)).toInt()

        return if(diffMin >= 60) {
            val min : Int = diffMin/60
            min.toString() + " " + application.resources.getString(R.string.hours)
        } else
            diffMin.toString() + " " + application.resources.getString(R.string.min)
    }
}