package com.example.myapplication3.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.AppSingleton
import com.example.myapplication3.db.EntityEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class ReportScreenState(
    val entities: List<EntityEntity> = emptyList(),
    val resultFile: String? = null,
)

class ReportViewModel : ViewModel() {
    private val entityDao = AppSingleton.provideDatabase().entitiesDao()
    private val json = Json { prettyPrint = true }

    private val _state = MutableStateFlow(ReportScreenState())
    val state: StateFlow<ReportScreenState> = _state

    init {
        viewModelScope.launch {
            entityDao.getEntities().collect { entities ->
                _state.update { it.copy(entities = entities.filter { it.price > 11.0 }) }
            }
        }
    }

    fun createReport() = _state.update { it.copy(resultFile = json.encodeToString(it.entities)) }
}
