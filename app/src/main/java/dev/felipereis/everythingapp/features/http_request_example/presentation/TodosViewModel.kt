package dev.felipereis.everythingapp.features.http_request_example.presentation

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import dev.felipereis.everythingapp.core.presentation.viewmodel.BaseViewModel
import dev.felipereis.everythingapp.features.http_request_example.domain.repositories.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todoRepository: TodoRepository,
) : BaseViewModel<TodosState, TodosEvent, TodosEffect>(TodosState.Empty) {
    override fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.LoadTodos -> onLoadTodos()
        }
    }

    private fun onLoadTodos() {
        _uiState.update { TodosState.Loading }

        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.getAll()
                .fold(
                    success = { todos ->
                        _uiState.update { TodosState.Loaded(todos) }
                    },
                    failure = { error ->
                        _uiState.update { TodosState.Error(error) }
                    }
                )
        }
    }
}