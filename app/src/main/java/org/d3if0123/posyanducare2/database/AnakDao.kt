package org.d3if0123.posyanducare2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0123.posyanducare2.model.Anak

@Dao
interface AnakDao {

    @Insert
    suspend fun insert(anak: Anak)

    @Update
    suspend fun update(anak: Anak)

    @Query("SELECT * FROM anak ORDER BY id DESC")
    fun getAnak(): Flow<List<Anak>>

    @Query("SELECT * FROM anak WHERE id = :id")
    suspend fun getAnakById(id: Long): Anak?

    @Query("DELETE FROM anak WHERE id = :id")
    suspend fun deleteById(id: Long)
}