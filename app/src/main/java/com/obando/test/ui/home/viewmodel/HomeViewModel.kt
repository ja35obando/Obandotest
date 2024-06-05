package com.obando.test.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.obando.test.core.base.BaseViewModel
import com.obando.test.core.models.WeatherModel
import com.obando.test.core.network.NetworkResult
import com.obando.test.core.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
)  : BaseViewModel() {

    private val _summary = MutableLiveData<WeatherModel?>()

    val summary: LiveData<WeatherModel?> get() = _summary

    fun getSummary() = viewModelScope.launch(Dispatchers.IO) {
        with(homeRepository.summary()) {
            when(this) {
                is NetworkResult.SuccessResponse -> {
                    responseObject.let {
                        _summary.postValue(it)
                    }
                }
                is NetworkResult.FailureResponse -> {
                    _summary.postValue(null)
                    validateCommonsErrors(this)
                }
            }
        }
    }
}