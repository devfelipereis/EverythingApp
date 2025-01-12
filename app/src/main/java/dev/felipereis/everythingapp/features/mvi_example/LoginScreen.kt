package dev.felipereis.everythingapp.features.mvi_example

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    // Here you could use something like Koin to inject the ViewModel
    viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(LoginUseCase()))
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LoginEffect.LoginError -> {
                    when (effect.error) {
                        AuthenticationError.UNKNOWN -> snackbarHostState.showSnackbar("Unknown error")
                        AuthenticationError.INVALID_CREDENTIALS -> snackbarHostState.showSnackbar("Invalid credentials")
                    }
                }

                is LoginEffect.LoginSuccess -> snackbarHostState.showSnackbar("Login success")
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        LoginContent(
            state = viewModel.uiState.collectAsStateWithLifecycle(),
            addEvent = viewModel::addEvent,
            localFocusManager = LocalFocusManager.current,
        )
    }
}

@Composable
fun LoginContent(
    state: State<LoginState>,
    addEvent: (LoginEvent) -> Unit,
    localFocusManager: FocusManager,
) {
    val inputColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        LoginForm(
            state = state.value,
            addEvent = addEvent,
            localFocusManager = localFocusManager,
            inputColors = inputColors
        )
    }
}

@Composable
fun LoginForm(
    state: LoginState,
    addEvent: (LoginEvent) -> Unit,
    localFocusManager: FocusManager,
    inputColors: TextFieldColors
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        SignInMessage()
        Spacer(modifier = Modifier.height(32.dp))
        InputField(
            label = "Email",
            value = state.email,
            onValueChange = { addEvent(LoginEvent.EmailChanged(it)) },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
            ),
            inputColors = inputColors,
            placeholder = "test@example.com",
            enabled = !state.isAuthenticating
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            value = state.password,
            onValueChange = { addEvent(LoginEvent.PasswordChanged(it)) },
            keyboardActions = KeyboardActions(
                onDone = { localFocusManager.clearFocus() }
            ),
            inputColors = inputColors,
            enabled = !state.isAuthenticating
        )
        Spacer(modifier = Modifier.height(32.dp))
        SignInButton(
            isAuthenticating = state.isAuthenticating,
            onClick = { addEvent(LoginEvent.LoginRequested) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpMessage()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SignInMessage() {
    Text(
        text = "Sign in to EverythingApp",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
    )
}


@Composable
fun InputField(
    label: String,
    value: String,
    placeholder: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    inputColors: TextFieldColors
) {
    Column {
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ),
            enabled = enabled,
            value = value,
            colors = inputColors,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            placeholder = { Text(text = placeholder) }
        )
    }
}

@Composable
fun PasswordField(
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    inputColors: TextFieldColors
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Forgot ?",
                style = MaterialTheme.typography.labelLarge.copy(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ),
            enabled = enabled,
            value = value,
            visualTransformation = PasswordVisualTransformation(),
            colors = inputColors,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = keyboardActions,
            placeholder = { Text(text = "123456") }
        )
    }
}

@Composable
fun SignInButton(isAuthenticating: Boolean, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        if (isAuthenticating) {
            CircularProgressIndicator(color = Color.White)
            return@TextButton
        }

        Text(
            text = "Sign In",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun SignUpMessage() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
        text = buildAnnotatedString {
            append("Don’t have an account? ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Sign up")
            }
        }
    )
}