package com.mionix.myapplication.viewModel

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedDataViewModel: ViewModel(){
    val inputNumber = MutableLiveData<String>()
    }