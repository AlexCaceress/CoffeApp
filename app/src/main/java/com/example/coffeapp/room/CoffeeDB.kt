package com.example.coffeapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CoffeeEntity::class], version = 3)
abstract class CoffeeDB : RoomDatabase() {

    abstract fun coffeeDAO(): CoffeeDAO

    companion object {

        @Volatile
        private var INSTANCE: CoffeeDB? = null

        fun getInstance(context: Context): CoffeeDB {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CoffeeDB::class.java,
                        name = "coffee_db"
                    ).build()
                }
                return instance
            }

        }

    }

}