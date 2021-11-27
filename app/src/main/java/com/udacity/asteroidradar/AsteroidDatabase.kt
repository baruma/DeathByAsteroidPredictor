package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// If you have more than one table, you should add them to this list
// When you change the schema you have to update the version number
@Database(entities = [Asteroid :: class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val asteroidDAO: AsteroidDAO

    // allows clients to access the methods without having to instantiate the class
    companion object {
        @Volatile  // ensures value of instance is always up to date and will not be cached.
        // Changes in one thread will be present to all other threads.  all writes/reads will be done from main
        private var INSTANCE: AsteroidDatabase? = null  // Instance keeps a reference to the database

        fun getInstance(context: Context): AsteroidDatabase { // This is a DB builder
            synchronized(this) {
                var instance = INSTANCE

                // Check if there is already a database or not
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AsteroidDatabase::class.java, "asteroid_database")
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

               return instance
            }
        }
    }


}