package dev.felipereis.everythingapp.features.http_request_example.data.datasources

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import dev.felipereis.everythingapp.core.data.remote.api.ApiEndpoints
import dev.felipereis.everythingapp.features.http_request_example.domain.dtos.TodoDTO
import dev.felipereis.everythingapp.features.http_request_example.domain.errors.TodoError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TodoRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getAll(): Result<List<TodoDTO>, TodoError> {
        return runCatching {
            httpClient.get("${ApiEndpoints.BASE_URL}${ApiEndpoints.TODOS}")
                .body<List<TodoDTO>>()
        }.mapError {
            TodoError.UNKNOWN
        }
    }
}