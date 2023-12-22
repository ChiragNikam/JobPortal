package com.brillect.jobportal.UIComponents

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelForComponents: ViewModel() {
    val _textState = MutableStateFlow("")
    val textState : StateFlow<String>
        get() = _textState

    fun updateTextState(newText: String) {
        _textState.value = newText
    }

}