package dev.felipereis.everythingapp.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, Effect>(
    initialState: State
) : ViewModel() {
    @Suppress("PropertyName")
    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    private val _effects = MutableSharedFlow<Effect>(extraBufferCapacity = 1)
    val effects: SharedFlow<Effect> = _effects

    init {
        startEventCollection()
    }

    private fun startEventCollection() {
        viewModelScope.launch {
            _events.collect { event ->
                onEvent(event)
            }
        }
    }

    protected abstract fun onEvent(event: Event)

    fun addEvent(event: Event) {
        _events.tryEmit(event)
    }

    protected fun addEffect(effect: Effect) {
        _effects.tryEmit(effect)
    }
}