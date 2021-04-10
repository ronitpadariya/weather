package com.github.ronit.weather.utility

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*

class LocationAddress {
    private val tag = "LocationAddress"
    fun getAddressFromLocation(
            latitude: Double,
            longitude: Double, context: Context, onFindCityListener: OnFindCityListener
    ) {
        val thread = object : Thread() {
            override fun run() {
                val geoCoder = Geocoder(
                        context,
                        Locale.getDefault()
                )
                var result: String = null.toString()
                try {
                    val addressList = geoCoder.getFromLocation(
                            latitude, longitude, 1
                    )
                    if ((addressList != null && addressList.size > 0)) {
                        val address = addressList.get(0)
                        val sb = StringBuilder()
                        /*for (i in 0 until address.maxAddressLineIndex) {
                            sb.append(address.getAddressLine(i)).append("\n")
                        }*/
                        sb.append(address.locality) //.append("\n")
                        Log.d("LocationAddress", "run: ${address.locality}")
//                        sb.append(address.postalCode).append("\n")
//                        sb.append(address.countryName)
                        result = sb.toString()
                    }
                } catch (e: IOException) {
                    Log.e(tag, "Unable connect to GeoCoder", e)
                } finally {
                    onFindCityListener.onFindCity(result)
                    /*val message = Message.obtain()
                    message.target = handler
                    message.what = 1
                    val bundle = Bundle()
                    *//*result = ("Latitude: " + latitude + " Longitude: " + longitude +
                            "\n\nAddress:\n" + result)*//*
                    bundle.putString("address", result)
                    message.data = bundle
                    message.sendToTarget()*/
                }
            }
        }
        thread.start()
    }
}
