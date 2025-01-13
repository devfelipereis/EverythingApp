package dev.felipereis.everythingapp.features.http_request_example.domain.models

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)