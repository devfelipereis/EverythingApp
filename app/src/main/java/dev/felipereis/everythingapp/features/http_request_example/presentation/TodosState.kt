package dev.felipereis.everythingapp.features.http_request_example.presentation

import dev.felipereis.everythingapp.features.http_request_example.domain.errors.TodoError
import dev.felipereis.everythingapp.features.http_request_example.domain.models.Todo

sealed class TodosState {
    data object Empty : TodosState()
    data object Loading : TodosState()
    data class Loaded(val todos: List<Todo>) : TodosState()
    data class Error(val error: TodoError) : TodosState()
}