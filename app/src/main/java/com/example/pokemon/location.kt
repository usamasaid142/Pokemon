package com.example.pokemon

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

class location {



    class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener {

        private lateinit var mMap: GoogleMap
        var currentlocation: Location? = null
        var latLng: LatLng? = null
        private lateinit var binding: ActivityMapsBinding
        var fusedLocationProviderClient: FusedLocationProviderClient? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMapsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            binding.mapview.onCreate(savedInstanceState)
            searchplace()


            if (isLocationPermissionAllowed()) {

                // call your function
                getUserLocation()

            } else     // request permission
            {

                requestLocationPermission()

            }
            binding.mapview.getMapAsync(this)
        }

        private fun requestLocationPermission() {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ).toString(),
                )
            ) {

                // فى حالة تم رفضه قيل ذلك مره يعمل برمشن
                // explain why you need the permission
                // عمل بوب لتوضيح سبب البرمش
                //  ثم طلب البرمشن

                Alert.showMessage(
                    this,
                    R.string.weneedpermissiontoaccesslocation,
                    R.string.worning,
                    true,
                    R.string.ok,
                ) { p0, _ ->
                    p0?.dismiss()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        1000
                    )

                }


            } else {

                // request permission

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )

            }
        }


        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            // Add a marker in Sydney and move the camera
            mMap.setOnMapClickListener(this)
            mMap.setOnInfoWindowClickListener(this)
            drawMarker()
        }


        var marker: Marker? = null
        private fun drawMarker() {
            val sydney = LatLng(20.0, 40.0)
            val sydney1 = LatLng(25.0, 45.0)
            val sydney2 = LatLng(30.0, 50.0)
            val sydney3 = LatLng(35.0, 55.0)
            val sydney4 = LatLng(0.0, 0.0)


            val lastlocaton = latLng?.let { LatLng(it.latitude, it.longitude) }
            if (lastlocaton == null || mMap == null) return
            lastlocaton?.let {
                if (marker == null)
                    marker = lastlocaton?.let { MarkerOptions().position(it).title("iam here") }
                        ?.let { mMap.addMarker(it) }
                mMap.setOnMarkerClickListener(this)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
                if (marker != null)
                    marker?.position =
                        lastlocaton?.let { LatLng(lastlocaton.latitude, it.longitude) }!!            //
                if (lastlocaton != null) {
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                lastlocaton.latitude,
                                lastlocaton.longitude
                            ), 15f
                        )
                    )
                }
            }


            // draw polyline **************
//        val polyline=PolylineOptions()
//        polyline.add(sydney)
//        polyline.add(sydney1)
//       val polylines:Polyline= mMap.addPolyline(polyline)
//

            // draw polygon***********
//        val m1 = LatLng(0.0, 0.0)
//        val m2 = LatLng(0.0, 5.0)
//        val m3= LatLng(5.0, 5.0)
//        val m4= LatLng(5.0, 0.0)
//        val points= mutableListOf<LatLng>()
//        points.add(m1)
//        points.add(m2)
//        points.add(m3)
//        points.add(m4)

//        var polygons= PolygonOptions()
//         .add(sydney,sydney1,sydney2,sydney3,sydney4)
//         .addHole(points)
//            .fillColor(Color.RED)
//        //    .strokeColor(Color.BLUE)
//        val polygon=mMap.addPolygon(polygons)
//         polygon.fillColor=R.color.teal_200


        }

        @SuppressLint("MissingPermission")
        fun getUserLocation() {

            val task = fusedLocationProviderClient?.lastLocation
            task?.addOnSuccessListener { location ->

                if (location != null) {
                    currentlocation = location
                    drawMarker()
                    Log.e("location", "${currentlocation?.latitude},${currentlocation?.longitude}")
                }
            }

        }


        fun isLocationPermissionAllowed(): Boolean {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
                return true
            return false
        }


        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                1000 -> {
                    // If request is cancelled, the result arrays are empty.
                    if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED) && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission is granted. Continue the action or workflow
                        // in your app.

                        getUserLocation()
                    } else {
                        // Explain to the user that the feature is unavailable because
                        // the features requires a permission that the user has denied.
                        // At the same time, respect the user's decision. Don't link to
                        // system settings in an effort to convince the user to change
                        // their decision.

                        Toast.makeText(this, " can not access Location ", Toast.LENGTH_SHORT).show()

                    }
                    return
                }


                // Add other 'when' lines to check for other
                // permissions this app might request.
                else -> {
                    // Ignore all other requests.
                }
            }
        }
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.

                    getUserLocation()
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }

        override fun onStart() {
            super.onStart()
            binding.mapview.onStart()
        }

        override fun onResume() {
            super.onResume()
            binding.mapview.onResume()
        }


        override fun onMarkerClick(p0: Marker): Boolean {

            Toast.makeText(this, " " + p0.position, Toast.LENGTH_LONG).show()
            return false
        }

        override fun onMarkerDrag(p0: Marker) {
            TODO("Not yet implemented")
        }

        override fun onMarkerDragEnd(p0: Marker) {
            TODO("Not yet implemented")
        }

        override fun onMarkerDragStart(p0: Marker) {
            TODO("Not yet implemented")
        }

        override fun onMapClick(p0: LatLng) {
            Toast.makeText(this, " " + p0.latitude, Toast.LENGTH_LONG).show()
        }

        override fun onInfoWindowClick(p0: Marker) {
            Toast.makeText(this, "info window", Toast.LENGTH_LONG).show()
        }


        private fun searchplace() {


            binding.search.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val location = binding.search.query.toString()
                    var addresslist: List<Address>? = null
                    if (!location.isNullOrEmpty()) {
                        val geocoder = Geocoder(this@MapsActivity)
                        try {

                            addresslist = geocoder.getFromLocationName(location, 1)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        val address = addresslist?.get(0)
                        address?.run {
                            latLng = LatLng(address.latitude, address.longitude)
//                            mMap.addMarker(MarkerOptions().position(latLng!!).title("Iam here.."))
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng!!))
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLng!!.latitude,latLng!!.longitude),15f))
                            drawMarker()

                        }

                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })


        }

    }


}