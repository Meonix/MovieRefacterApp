package com.nice.app_ex.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel ():ViewModel() {

    protected val _messageError by lazy {
        MutableLiveData<String>()
    }

    protected val _messageSuccess by lazy {
        MutableLiveData<String>()
    }

    val messageError:LiveData<String>
    get() = _messageError

    val messageSuccess:LiveData<String>
    get() = _messageSuccess


    fun executeUseCase(action: suspend () -> Unit) {
        viewModelScope.launch {
            action()
        }
    }
}