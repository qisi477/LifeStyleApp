package com.example.lifestyleapp.common

data class Location(
    var city: String?,
    var countryCode: String?,
    var stateCode: String?,
    var zipCode: String? = null,
    var latitude: Float? = null,
    var longitude: Float? = null,
)


fun getNearbyHikingTrails(location: Location) {
    //TODO("getNearbyHikingTrails Not implemented")
    //api call to alltrails
    //store and set relevant info
}
