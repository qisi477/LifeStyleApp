package com.example.lifestyleapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.HeavyWorker
import com.example.lifestyleapp.common.TAG_WEATHER
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlin.math.roundToInt

private const val CITY = "city"
private const val COUNTRY = "country"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.weatherRepository = HeavyWorker()
        if (savedInstanceState == null) {
            arguments?.let {
                val city = it.getString(CITY)
                val country = it.getString(COUNTRY)
                Log.d(TAG_WEATHER, "$city $country")
                weatherViewModel.onViewCreated(city, country)
            }
        }
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            updateView(weather)
        }
    }

    private fun updateView(weather: Weather) {
        location_tv?.text = "${weather.city}, ${weather.sys.countryCode}"
        //todo add more fields
        temperature_tv?.text = weather.mainWeather.getTempFahrenheit().roundToInt().toString()
        feels_like_tv?.text =
            weather.mainWeather.getFeelsLikeTempFahrenheit().roundToInt().toString()
        temp_min_tv?.text = weather.mainWeather.getTempMinFahrenheit().roundToInt().toString()
        temp_max_tv?.text = weather.mainWeather.getTempMaxFahrenheit().roundToInt().toString()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WeatherFragment.
         */
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