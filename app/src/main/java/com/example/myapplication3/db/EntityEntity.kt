package com.example.myapplication3.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

internal const val ENTITY_TABLE_NAME = "entities"

@Entity(tableName = ENTITY_TABLE_NAME)
@Serializable
data class EntityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val price: Double,
    // FIXME(1: добавить поле в сущность БД)
    val contractConfirmed: Boolean,
)
