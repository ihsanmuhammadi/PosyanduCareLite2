package org.d3if0123.posyanducare2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anak")
data class Anak(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val usia: String,
    val gender: String
)