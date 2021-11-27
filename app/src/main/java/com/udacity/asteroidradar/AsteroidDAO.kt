package com.udacity.asteroidradar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsteroidDAO {
    @Insert
    suspend fun insertAsteroids(asteroid: List<Asteroid>)

    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    suspend fun getAllAsteroids(): List<Asteroid>

// TODO: Fetch and display the asteroids from the database and only fetch the asteroids from today onwards,
//  ignoring asteroids before today.

// TODO: Also, display the asteroids sorted by date (Check SQLite documentation to get sorted data using a query).

}