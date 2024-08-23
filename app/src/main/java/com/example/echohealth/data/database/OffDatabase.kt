package com.example.echohealth.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.echohealth.data.dao.ExposureDataDao
import com.example.echohealth.data.entity.ExposureData
@Database(entities =[ExposureData::class], version = 1, exportSchema = false)
abstract class OffDatabase:RoomDatabase() {
    abstract fun ExposureDataDao():ExposureDataDao
    companion object{
        @Volatile
        private var INSTANCE:OffDatabase?= null
        fun getDataBase(context:Context):OffDatabase{
            val inst= INSTANCE
            if(inst!=null){
                return inst
            }
            synchronized(this){
                val newinst= Room.databaseBuilder(context.applicationContext,OffDatabase::class.java, name = "OffDatabase").build()
                  INSTANCE=newinst
                return newinst
            }
        }
    }
}