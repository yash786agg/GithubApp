package com.app.ui.main

import androidx.lifecycle.ViewModel
import com.app.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository : MainRepository) : ViewModel() {

}