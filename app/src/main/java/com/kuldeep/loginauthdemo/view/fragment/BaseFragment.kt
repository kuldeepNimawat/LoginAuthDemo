package com.kuldeep.loginauthdemo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.kuldeep.loginauthdemo.data.UserPreferences
import com.kuldeep.loginauthdemo.data.repository.BaseRepository
import com.kuldeep.loginauthdemo.data.api.RemoteDataSource
import com.kuldeep.loginauthdemo.data.api.UserApi
import com.kuldeep.loginauthdemo.util.startNewActivity
import com.kuldeep.loginauthdemo.view.acitivity.AuthActivity
import com.kuldeep.loginauthdemo.viewmodel.BaseViewModel
import com.kuldeep.loginauthdemo.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel,B:ViewBinding,R: BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: B
    protected lateinit var viewModel:VM
    protected val remoteDataSource= RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences= UserPreferences(requireContext())
        binding=getFragmentBinding(inflater,container)
        val factory= LoginViewModelFactory(getFragmentRepository())
        viewModel= ViewModelProvider(this,factory).get(getViewModel())
        lifecycleScope.launch {
            userPreferences.authToken.first()
        }

        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):B

    abstract fun getFragmentRepository(): R

    fun logout()=lifecycleScope.launch {
        val auth_token=userPreferences.authToken.first()
        val api=remoteDataSource.buildApi(UserApi::class.java,auth_token)
        viewModel.logout(api)
        userPreferences.clearToken()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

}