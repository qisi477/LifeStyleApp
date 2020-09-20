package com.example.lifestyleapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val CITY = "city"
private const val COUNTRY = "country"
//todo crashed on rotate
/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    private var city: String? = null
    private var country: String? = null
    var weather: Weather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(CITY)
            country = it.getString(COUNTRY)
        }
        Log.d(TAG_WEATHER, "$city $country")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d(TAG_CH, "Initialize Weather Fragment")
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val job = GlobalScope.launch(Dispatchers.IO) {
            weather = suspendGetWeatherFromInternet(Location(city = city, country = country))
            updateView()
        }
        job.start()
    }

    private fun updateView() {
        if (city != "null" && country != "null") {
            val loc = "$city, $country"
            location_tv.text = loc
        }
        //todo add more fields
        temperature_tv.text = weather?.mainWeather?.getTempFahrenheit().toString()
        feels_like_tv.text = weather?.mainWeather?.getFeelsLikeTempFahrenheit().toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(usr: UserDataModel) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(COUNTRY, usr.country)
                    putString(CITY, usr.city)
                }
            }
    }

}