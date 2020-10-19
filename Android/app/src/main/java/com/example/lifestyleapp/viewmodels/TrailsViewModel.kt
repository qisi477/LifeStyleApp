package com.example.lifestyleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Trail
import com.example.lifestyleapp.common.TrailRepositoryHolder
import com.example.lifestyleapp.repositories.TrailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrailsViewModel : ViewModel() {
    private val _trailLiveData: MutableLiveData<List<Trail>?> by lazy {
        MutableLiveData<List<Trail>?>()
    }

    val trailLiveData: LiveData<List<Trail>?> = _trailLiveData

    //todo not used outside of testing
    lateinit var trailRepository: TrailRepository

    fun onViewCreated(city: String?, country: String?, latitude: Float, longitude: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedTrails: List<Trail>? =
                TrailRepositoryHolder().loadTrails(
                    Location(
                        city = city,
                        country = country,
                        latitude = latitude,
                        longitude = longitude
                    )
                )
            _trailLiveData.postValue(fetchedTrails)
        }
    }
}

