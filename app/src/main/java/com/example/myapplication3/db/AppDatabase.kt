package com.example.myapplication3.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        EntityEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entitiesDao(): EntitiesDao
}