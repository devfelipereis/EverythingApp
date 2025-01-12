package dev.felipereis.everythingapp.features.mvi_example

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.delay
import kotlin.random.Random

class LoginUseCase {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit, AuthenticationError> {
        delay(2000)
        
        if (email == "test@example.com" && password == "123456") {
            return Ok(Unit)
        }

        if (Random.nextBoolean()) {
            return Err(AuthenticationError.UNKNOWN)
        }

        return Err(AuthenticationError.INVALID_CREDENTIALS)
    }
}