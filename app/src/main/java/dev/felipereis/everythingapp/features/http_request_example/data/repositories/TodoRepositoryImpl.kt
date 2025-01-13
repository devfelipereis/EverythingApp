package dev.felipereis.everythingapp.features.http_request_example.data.repositories

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import dev.felipereis.everythingapp.features.http_request_example.data.datasources.TodoRemoteDataSource
import dev.felipereis.everythingapp.features.http_request_example.domain.dtos.asDomainModel
import dev.felipereis.everythingapp.features.http_request_example.domain.errors.TodoError
import dev.felipereis.everythingapp.features.http_request_example.domain.models.Todo
import dev.felipereis.everythingapp.features.http_request_example.domain.repositories.TodoRepository

class TodoRepositoryImpl(
    private val dataSource: TodoRemoteDataSource
) : TodoRepository {
    override suspend fun getAll(): Result<List<Todo>, TodoError> {
        return dataSource.getAll().map { it.map { it.asDomainModel() } }
    }
}