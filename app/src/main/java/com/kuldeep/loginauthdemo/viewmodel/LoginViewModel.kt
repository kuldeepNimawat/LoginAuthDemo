package com.kuldeep.loginauthdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuldeep.loginauthdemo.data.repository.LoginRepository
import com.kuldeep.loginauthdemo.data.api.Resource
import com.kuldeep.loginauthdemo.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) :BaseViewModel(loginRepository) {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
    get()=_loginResponse
    fun login(
        email: String,
        password: String
    )= viewModelScope.launch {
        _loginResponse.value=Resource.loading
        _loginResponse.value=loginRepository.login(email,password)
    }

    suspend fun saveAuthToken(token: String){
         loginRepository.saveAuthToken(token)
    }
}