package com.example.lifestyleapp.repositories

import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Trail

interface TrailRepository {

    suspend fun loadTrails(location: Location): List<Trail>?

}