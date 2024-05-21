package com.example.myapplication3.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.AppSingleton
import com.example.myapplication3.db.EntityEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainScreenState(
    val entities: List<EntityEntity> = emptyList(),
)

class MainViewModel : ViewModel() {
    private val entityDao = AppSingleton.provideDatabase().entitiesDao()

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {
        viewModelScope.launch {
            entityDao.getEntities().collect { entities ->
                _state.update {
                    it.copy(entities = entities)
                }
            }
        }
    }
}
