package dev.felipereis.everythingapp.features.http_request_example.presentation

sealed interface TodosEvent {
    data object LoadTodos : TodosEvent
}