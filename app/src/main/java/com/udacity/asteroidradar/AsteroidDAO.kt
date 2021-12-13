package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroid: List<Asteroid>)

    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    suspend fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroid_table where close_approach_date >= :date ORDER BY date(close_approach_date)")
    suspend fun getAsteroidsFuture(date: String): List<Asteroid>

}