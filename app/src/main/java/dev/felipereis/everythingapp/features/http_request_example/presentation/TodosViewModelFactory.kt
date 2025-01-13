package dev.felipereis.everythingapp.features.http_request_example.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.felipereis.everythingapp.features.http_request_example.domain.repositories.TodoRepository

class TodosViewModelFactory(
    private val todoRepository: TodoRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodosViewModel::class.java)) {
            return TodosViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}