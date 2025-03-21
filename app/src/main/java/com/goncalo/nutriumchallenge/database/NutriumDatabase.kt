package com.goncalo.nutriumchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.goncalo.nutriumchallenge.professionals.data.database.ProfessionalDao
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional

@Database(entities = [Professional::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverters::class)
abstract class NutriumDatabase: RoomDatabase() {
    abstract fun professionalDao(): ProfessionalDao
}