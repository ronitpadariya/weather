package com.github.ronit.weather.ui.weatherinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity(){

    private val weatherInfoVM by viewModels<WeatherInfoVM>()

    companion object{
        const val KEY_CITY = "KEY_CITY"

        fun getIntent(city: String, context: Context): Intent {
            val intent = Intent(context, WeatherInfoActivity::class.java)
            intent.putExtra(KEY_CITY, city)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = intent.getStringExtra(KEY_CITY)

        city?.let {
            weatherInfoVM.getCurrentWeatherInfo(it)
            weatherInfoVM.getForecastInfo(it)
        }

        weatherInfoVM.forecastLiveData.observe(this, {
            if(it.isSuccess){

            } else {
                it.error?.let { it1 -> showErrorDialog(it1) }
            }
        })

        weatherInfoVM.currentLiveData.observe(this, {
            if(it.isSuccess){

            } else {
                it.error?.let { it1 -> showErrorDialog(it1) }
            }
        })
    }

    private fun showErrorDialog(error: String){
        Timber.d("==================Error $error")
        MaterialAlertDialogBuilder(this)
            .setTitle(error)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, _ ->
                dialog?.dismiss()
                finish()
            }
            .show()
    }

}