package com.example.healthylifestyleapp.utils

object AppConstants {
    val IMAGE_SIZE: Int = 5000000
    /**
     * val used for storing request code for accessing camera and asking permission
     */
    val REQUEST_CODE_CAMERA_PERMISSION = 0
    /**
     * val used for storing request code for accessing gallery and asking permission
     */
    val REQUEST_CODE_GALLERY_PERMISSION = 1
    /**
     * val use for cuisine type
     */
    val REQUEST_CODE_CUISINE_TYPE = 2
    /**
     * val use for dining style
     */
    val REQUEST_CODE_DINING_STYLE = 3
    /**
     * Integer is used for launching the Camera activity with request code 1
     */
    val REQUEST_IMAGE_CAPTURE: Int = 1

    /**
     * String used for cuisine type
     */
    val CUISINE_TYPE = "Cusine"

    /**
     * String used for dining style
     */
    val DINING_STYLE = "Dining"

    /**
     * String use for cuisine and dining type
     */
    val CUISINE_OR_DINING_TYPE = "type"

    /**
     * String is used for getting result from place autocomplete activity
     */
    val PLACE_AUTOCOMPLETE = 4

    /**
     * String is used for specifying setting package
     */
    val PACKAGE_CONSTANT = "package"

    /**
     * String is used to store sub locality
     */
    val SUB_LOCALITY_2 = "sublocality_level_2"

    /**
     * String is used to store sub locality
     */
    val SUB_LOCALITY_1 = "sublocality_level_1"

    /**
     * String is used to store locality
     */
    val LOCALITY = "locality"

    /**
     * String is used to store area
     */
    val ADMIN_AREA2 = "administrative_area_level_2"

    /**
     * String is used to store area
     */
    val ADMIN_AREA1 = "administrative_area_level_1"

    /**
     * String is used to store country
     */
    val COUNRTY = "country"

    /**
     * String is used to store postal code
     */
    val POSTAL_CODE = "postal_code"

    /**
     * Integer used for location request code
     */
    val LOCATION_REQUEST_CODE = 1
}