package dev.felipereis.everythingapp.features.mvi_example

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class LoginUseCaseTest {
    private val loginUseCase = LoginUseCase()

    @Test
    fun `should return Ok when login is successful`(): Unit = runTest {
        val email = "test@example.com"
        val password = "123456"

        val result: Result<Unit, AuthenticationError> = loginUseCase.invoke(email, password)

        assertEquals(Ok(Unit), result)
    }

    @Test
    fun `should return Err when login fails`(): Unit = runTest {
        val email = "test@example.com"
        val password = "password"

        val result: Result<Unit, AuthenticationError> = loginUseCase.invoke(email, password)

        assertIs<AuthenticationError>(result.error)
    }


}