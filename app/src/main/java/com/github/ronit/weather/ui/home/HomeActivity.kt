package com.github.ronit.weather.ui.home

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.ronit.weather.R
import com.github.ronit.weather.databinding.ActivityHomeBinding
import com.github.ronit.weather.ui.weatherinfo.WeatherInfoActivity
import com.github.ronit.weather.utility.PermissionCallback
import com.github.ronit.weather.utility.PermissionManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), OnMapReadyCallback, PermissionCallback {

    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val homeVM by viewModels<HomeViewModel>()
    private lateinit var mMap: GoogleMap
    private var lm: LocationManager? = null
    private var isLocationGranted: Boolean = false

    companion object {
        const val REQUEST_ENABLE_LOCATION = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()
    }


    private fun initView() {
        binding.container.adapter = CityListAdapter()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocation()

        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            Timber.d("onMapReady: ${latLng.latitude} ${latLng.longitude}")
            homeVM.findCityBasedOnLatLng(latLng.latitude, latLng.longitude, this)
        }

        homeVM.cityLiveData.observe(this, { locationDetails ->

            val latLng = LatLng(locationDetails.latitude, locationDetails.longitude)
            val infoWindow = mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(locationDetails.city)
                    .snippet("Click here to See ${locationDetails.city} Weather Info")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            infoWindow.showInfoWindow()

            mMap.setOnInfoWindowClickListener {
                mMap.clear()
                val intent = WeatherInfoActivity.getIntent(locationDetails.city, this)
                startActivity(intent)
            }
        })
    }

    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            if (isLocationEnabled()) {
                displayLocationSettingsRequest()
            } else {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(it.latitude, it.longitude), 16.0f
                            )
                        )
                    }
                }

            }
        } else {
            handlePermission()
        }
    }

    private fun handlePermission() {
        if (!isLocationGranted) {
//            _isStartLoadingPage.value = false
            Timber.d("========================Request Location")
            PermissionManager.locationPermission(this, this)
        } else {
            if (!isLocationEnabled()) {
                displayLocationSettingsRequest()
            }
        }
    }

    override fun permissionGranted(whichPermission: List<String>) {
        isLocationGranted = true
        enableMyLocation()
        if (!isLocationEnabled()) {
            displayLocationSettingsRequest()
        }
    }

    override fun permissionDecline(whichPermission: List<String>) {
        isLocationGranted = false
    }

    private fun isLocationEnabled(): Boolean {
        return lm?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    private fun displayLocationSettingsRequest() {

        val mLocationRequest = LocationRequest.create()
        // .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder()
            .setNeedBle(true)
            .addLocationRequest(mLocationRequest)
        //.addLocationRequest(mLocationRequestBalancedPowerAccuracy)

        val result = LocationServices.getSettingsClient(this)
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {
                val response = result.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.

                Timber.d("============================${result}======$response")

            } catch (exception: ApiException) {
                // exception.printStackTrace()
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as (ResolvableApiException)
                            // Show the dialog by calling startResolutionForResult(),
                            resolvable.startResolutionForResult(
                                this,
                                REQUEST_ENABLE_LOCATION
                            )
                            // and check the result in onActivityResult().

                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_LOCATION && resultCode == RESULT_OK) {
            enableMyLocation()
        }
    }

}