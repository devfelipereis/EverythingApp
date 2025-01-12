package dev.felipereis.everythingapp.features.mvi_example

import dev.felipereis.everythingapp.MainDispatcherRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = mockk()
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `should change email when EmailChanged event is added`() = runTest {
        val email = "test@test.com"

        viewModel.addEvent(LoginEvent.EmailChanged(email))

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(email, state.email)
    }

    @Test
    fun `should change password when EmailChanged event is added`() = runTest {
        val password = "123456"

        viewModel.addEvent(LoginEvent.PasswordChanged(password))

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(password, state.password)
    }
}