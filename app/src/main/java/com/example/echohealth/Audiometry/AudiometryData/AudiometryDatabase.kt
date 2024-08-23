package com.example.echohealth.Audiometry.AudiometryData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AudiometryResult::class], version = 1,exportSchema = false)
abstract class AudiometryDatabase : RoomDatabase() {
    abstract fun audiometryDao(): AudiometryDao
    companion object {
        @Volatile
        private var INSTANCE: AudiometryDatabase? = null
        fun fetchDatabase(context: Context): AudiometryDatabase {
            val inst= INSTANCE
            if(inst!=null){
                return inst
            }
            synchronized(this) {
                val newinstance = Room.databaseBuilder(
                    context.applicationContext,
                    AudiometryDatabase::class.java,
                    "audiometry_database"
                ).build()
                INSTANCE = newinstance
                return newinstance
            }
        }
    }
}

