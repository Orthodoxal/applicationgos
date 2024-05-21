package com.example.myapplication3.entity_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.AppSingleton
import com.example.myapplication3.db.EntityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EntityInfoScreenState(
    val name: String = "",
    val price: String = "",
    // FIXME(2: добавить поле в состояние экрана информации о сущности)
    val contractConfirmed: Boolean = false,
    val isEdit: Boolean = false,
)

class EntityInfoViewModel : ViewModel() {
    private val entityDao = AppSingleton.provideDatabase().entitiesDao()
    private var entity: EntityEntity? = null

    private val _state = MutableStateFlow(EntityInfoScreenState())
    val state: StateFlow<EntityInfoScreenState> = _state

    fun applyArgs(entity: EntityEntity?) = _state.update {
        this.entity = entity

        val res = if (entity != null) {
            // FIXME(3: Передаем полученные аргументы с прошлого экрана в состояние текущего экрана)
            EntityInfoScreenState(entity.name, entity.price.toString(), entity.contractConfirmed, isEdit = true)
        } else {
            EntityInfoScreenState()
        }
        res
    }

    fun onNameChange(name: String) = _state.update { it.copy(name = name) }

    fun onPriceChange(price: String) = _state.update { it.copy(price = price) }

    // FIXME(7: добавить логику для изменения поля на экране)
    fun onContractConfirmChange(contractConfirmed: Boolean) =
        _state.update { it.copy(contractConfirmed = contractConfirmed) }

    fun createOrUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val id = entity?.id) {
                null -> entityDao.createEntity(EntityEntity(id = null))
                else -> entityDao.updateEntity(EntityEntity(id = id))
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            entity?.let { entityDao.deleteEntity(it) }
        }
    }

    private fun EntityEntity(id: Long?) =
        EntityEntity(
            id = id,
            name = state.value.name,
            price = state.value.price.takeIf { it.isNotEmpty() }?.toDouble() ?: 0.0,
            // FIXME(4: Добавляем сюда новое свойство)
            contractConfirmed = state.value.contractConfirmed,
        )
}
