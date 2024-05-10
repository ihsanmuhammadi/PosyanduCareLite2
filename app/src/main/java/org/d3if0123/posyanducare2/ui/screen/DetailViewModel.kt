package org.d3if0123.posyanducare2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0123.posyanducare2.database.AnakDao
import org.d3if0123.posyanducare2.model.Anak

class DetailViewModel(private val dao: AnakDao) : ViewModel() {
    fun insert(nama: String, usia: String, gender: String) {
        val catatan = Anak(
            nama = nama,
            usia = usia,
            gender = gender
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    suspend fun getAnak(id: Long): Anak? {
        return dao.getAnakById(id)
    }

    fun update(id: Long, nama: String, usia: String, gender: String) {
        val anak = Anak(
            id = id,
            nama = nama,
            usia = usia,
            gender = gender
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(anak)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}