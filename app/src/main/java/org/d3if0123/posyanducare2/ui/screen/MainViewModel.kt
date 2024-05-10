package org.d3if0123.posyanducare2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0123.posyanducare2.database.AnakDao
import org.d3if0123.posyanducare2.model.Anak

class MainViewModel(dao: AnakDao) : ViewModel() {

    val data: StateFlow<List<Anak>> = dao.getAnak().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}