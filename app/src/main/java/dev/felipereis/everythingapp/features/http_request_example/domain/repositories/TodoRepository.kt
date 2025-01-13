package dev.felipereis.everythingapp.features.http_request_example.domain.repositories

import com.github.michaelbull.result.Result
import dev.felipereis.everythingapp.features.http_request_example.domain.errors.TodoError
import dev.felipereis.everythingapp.features.http_request_example.domain.models.Todo

interface TodoRepository {
    suspend fun getAll(): Result<List<Todo>, TodoError>
}