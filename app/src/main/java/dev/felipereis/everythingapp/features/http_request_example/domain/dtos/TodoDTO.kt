package dev.felipereis.everythingapp.features.http_request_example.domain.dtos

import dev.felipereis.everythingapp.features.http_request_example.domain.models.Todo
import kotlinx.serialization.Serializable

@Serializable
data class TodoDTO(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

fun TodoDTO.asDomainModel() = Todo(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)