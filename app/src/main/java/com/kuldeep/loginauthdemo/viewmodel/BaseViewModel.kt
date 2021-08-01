package com.kuldeep.loginauthdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.kuldeep.loginauthdemo.data.api.UserApi
import com.kuldeep.loginauthdemo.data.repository.BaseRepository
import com.kuldeep.loginauthdemo.data.repository.UserRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {
    suspend fun logout(api: UserApi) = repository.logout(api)
}