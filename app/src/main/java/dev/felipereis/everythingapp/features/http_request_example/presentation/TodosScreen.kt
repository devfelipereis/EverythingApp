package dev.felipereis.everythingapp.features.http_request_example.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.felipereis.everythingapp.core.data.remote.api.KtorHttpClientFactory
import dev.felipereis.everythingapp.features.http_request_example.data.datasources.TodoRemoteDataSource
import dev.felipereis.everythingapp.features.http_request_example.data.repositories.TodoRepositoryImpl
import dev.felipereis.everythingapp.features.http_request_example.domain.errors.TodoError
import dev.felipereis.everythingapp.features.http_request_example.domain.models.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
    onNavigateBack: () -> Unit,
    // Here you could use something like Koin to inject the ViewModel and resolve the dependencies
    viewModel: TodosViewModel = viewModel(
        factory = TodosViewModelFactory(
            todoRepository = TodoRepositoryImpl(
                dataSource = TodoRemoteDataSource(httpClient = KtorHttpClientFactory.create())
            )
        )
    )
) {
    val uiState = viewModel.uiState.collectAsState()
    val addEvent = viewModel::addEvent

    LaunchedEffect(Unit) {
        if (uiState.value is TodosState.Empty) {
            addEvent(TodosEvent.LoadTodos)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Todos") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back to home screen"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.value,
            label = "content",
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
        ) { state ->
            when (state) {
                TodosState.Empty -> EmptyContent()
                TodosState.Loading -> LoadingContent()
                is TodosState.Loaded -> TodosList(state.todos)
                is TodosState.Error -> ErrorContent(state.error)
            }
        }
    }
}


@Composable
fun EmptyContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No todos found")
    }
}

@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(error: TodoError) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "An error occurred: $error")
    }
}

@Composable
fun TodosList(todos: List<Todo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(todos) { index, todo ->
            Text(text = todo.title)
            if (index < todos.lastIndex)
                HorizontalDivider(color = Color.Black, thickness = 1.dp)
        }
    }
}
