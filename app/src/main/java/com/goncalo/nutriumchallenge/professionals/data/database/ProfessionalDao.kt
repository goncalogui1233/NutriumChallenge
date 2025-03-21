package com.goncalo.nutriumchallenge.professionals.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional

@Dao
interface ProfessionalDao {

    @Query("SELECT * from Professional where id LIKE (:id)")
    fun getProfessionalById(id: String): Professional?

    @Query("SELECT * from Professional where uniqueId LIKE (:id)")
    fun getProfessionalByUniqueId(id: String): Professional?

    @Query("SELECT * from Professional")
    fun getProfessionalPagingSource(): PagingSource<Int, Professional>

    @Query("SELECT * FROM Professional")
    suspend fun getAllProfessionalItems(): List<Professional>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfessional(professional: Professional)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertALlProfessional(professionalList: List<Professional>)

    @Update
    suspend fun updateProfessional(professional: Professional)

    @Delete
    suspend fun deleteProfessional(professional: Professional)

    @Query("DELETE FROM Professional")
    suspend fun clearProfessionalTable()

}