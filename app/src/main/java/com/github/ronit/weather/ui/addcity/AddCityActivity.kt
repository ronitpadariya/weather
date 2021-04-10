package com.github.ronit.weather.ui.addcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.github.ronit.weather.R
import com.github.ronit.weather.databinding.ActivityAddCityBinding

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddCityActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val mBinding: ActivityAddCityBinding by lazy { ActivityAddCityBinding.inflate(layoutInflater) }

    private val addCityVM by viewModels<AddCityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { latLng ->
//            Toast.makeText(this, "${latlng.latitude} ${latlng.longitude}", Toast.LENGTH_LONG).show()
            addCityVM.findCityBasedOnLatLng(latLng.latitude, latLng.longitude, this)
            /*val locationAddress = LocationAddress()
            locationAddress.getAddressFromLocation(
                    latlng.latitude, latlng.longitude, applicationContext, GeoCodeHandler(this)
            )*/
        }

        addCityVM.cityLiveData.observe(this, { city ->
            mBinding.tvCityName.setText(getString(R.string.city_with_title, city))

            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.city_found, city))
                .setPositiveButton(android.R.string.ok
                ) { dialog, _ ->
                    dialog?.dismiss()
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }
                .show()
        })

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}