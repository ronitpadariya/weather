package com.github.ronit.weather.ui.weatherinfo

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.ronit.weather.R
import com.github.ronit.weather.data.Report
import com.github.ronit.weather.databinding.ActivityCityDetailsBinding
import com.github.ronit.weather.utility.TextViewFactory
import com.github.ronit.weather.utility.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity(){

    private val weatherInfoVM by viewModels<WeatherInfoVM>()
    private val binding: ActivityCityDetailsBinding by lazy { ActivityCityDetailsBinding.inflate(
        layoutInflater
    ) }
    private var isLoad = false

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
        setContentView(binding.root)

        initView()
        observerLiveDate()
        callAPIs()

    }

    private fun initView(){
        val typeface = Typeface.createFromAsset(assets, "fonts/Vazir.ttf")
        binding.tempTextView.setFactory(
            TextViewFactory(
                this,
                R.style.TempTextView,
                true,
                typeface
            )
        )
        binding.tempTextView.setInAnimation(
            this,
            R.anim.slide_in_right
        )
        binding.tempTextView.setOutAnimation(
            this,
            R.anim.slide_out_left
        )

        binding.descriptionTextView.setFactory(
            TextViewFactory(
                this,
                R.style.DescriptionTextView,
                true,
                typeface
            )
        )
        binding.descriptionTextView.setInAnimation(
            this,
            R.anim.slide_in_right
        )
        binding.descriptionTextView.setOutAnimation(
            this,
            R.anim.slide_out_left
        )
        binding.humidityTextView.setFactory(
            TextViewFactory(
                this,
                R.style.HumidityTextView,
                false,
                typeface
            )
        )
        binding.humidityTextView.setInAnimation(
            this,
            R.anim.slide_in_bottom
        )
        binding.humidityTextView.setOutAnimation(
            this,
            R.anim.slide_out_top
        )
        binding.windTextView.setFactory(
            TextViewFactory(
                this,
                R.style.WindSpeedTextView,
                false,
                typeface
            )
        )
        binding.windTextView.setInAnimation(
            this,
            R.anim.slide_in_bottom
        )
        binding.windTextView.setOutAnimation(
            this,
            R.anim.slide_out_top
        )


        binding.main.setOnRefreshListener {
            isLoad = true
            callAPIs()
        }
    }

    private fun callAPIs(){
        val city = intent.getStringExtra(KEY_CITY)

        city?.let {
            binding.cityNameTextView.text = it
            weatherInfoVM.getCurrentWeatherInfo(it)
            weatherInfoVM.getForecastInfo(it)
        }
    }

    private fun observerLiveDate(){
        weatherInfoVM.forecastLiveData.observe(this, {
            if (it.isSuccess) {

            } else {
                it.error?.let { it1 -> showErrorDialog(it1) }
            }
        })

        weatherInfoVM.currentLiveData.observe(this, {
            if (it.isSuccess) {
                binding.main.isRefreshing = false
                showCurrentWeatherInfo(it.report)
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

    private fun showCurrentWeatherInfo(report: Report?){
        report?.let {
            if(isLoad) {
                binding.tempTextView.setText(
                    String.format(
                        Locale.getDefault(),
                        "%.0f°",
                        it.main?.temp ?: 0.00
                    )
                )
                binding.descriptionTextView.setText(
                    String.format(
                        Locale.getDefault(), Utils.getWeatherStatus(
                            it.weather?.get(
                                0
                            )?.id ?: 0
                        )
                    )
                )
                binding.humidityTextView.setText(
                    String.format(
                        Locale.getDefault(),
                        "%d%%",
                        it.main?.humidity ?: 0.00
                    )
                )
                binding.windTextView.setText(
                    String.format(
                        Locale.getDefault(), resources.getString(
                            R.string.wind_unit_label
                        ), it.wind?.speed ?: 0.00
                    )
                )
            } else {
                Timber.d("====================>${it.main?.temp}")
                binding.tempTextView.setCurrentText(
                    String.format(
                        Locale.getDefault(),
                        "%.0f°",
                        it.main?.temp ?: 0.00
                    )
                )
                binding.descriptionTextView.setCurrentText(
                    String.format(
                        Locale.getDefault(), Utils.getWeatherStatus(
                            it.weather?.get(
                                0
                            )?.id ?: 0
                        )
                    )
                )
                binding.humidityTextView.setCurrentText(
                    String.format(
                        Locale.getDefault(),
                        "%d%%",
                        it.main?.humidity ?: 0.00
                    )
                )
                binding.windTextView.setCurrentText(
                    String.format(
                        Locale.getDefault(), resources.getString(
                            R.string.wind_unit_label
                        ), it.wind?.speed ?: 0.00
                    )
                )
            }

        }
    }




}