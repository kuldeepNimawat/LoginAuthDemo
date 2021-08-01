package com.kuldeep.loginauthdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuldeep.loginauthdemo.data.repository.BaseRepository
import com.kuldeep.loginauthdemo.data.repository.LoginRepository
import com.kuldeep.loginauthdemo.data.repository.UserRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java)->LoginViewModel(repository as LoginRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java)->HomeViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModel Class not found")
        }
    }


}