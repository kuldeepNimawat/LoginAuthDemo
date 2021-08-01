package com.kuldeep.loginauthdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuldeep.loginauthdemo.data.api.Resource
import com.kuldeep.loginauthdemo.data.repository.UserRepository
import com.kuldeep.loginauthdemo.data.response.LoginResponse
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
    ) : BaseViewModel(userRepository) {
        private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        val user: LiveData<Resource<LoginResponse>>
        get() = _user

    fun getUser()=viewModelScope.launch {
        _user.value=Resource.loading
        _user.value=userRepository.getUser()
    }
}