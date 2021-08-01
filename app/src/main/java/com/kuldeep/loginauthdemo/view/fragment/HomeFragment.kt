package com.kuldeep.loginauthdemo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.kuldeep.loginauthdemo.R
import com.kuldeep.loginauthdemo.data.api.Resource
import com.kuldeep.loginauthdemo.data.api.UserApi
import com.kuldeep.loginauthdemo.data.repository.UserRepository
import com.kuldeep.loginauthdemo.data.response.User
import com.kuldeep.loginauthdemo.databinding.HomefragmentBinding
import com.kuldeep.loginauthdemo.util.visible
import com.kuldeep.loginauthdemo.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel,HomefragmentBinding,UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeprogressbar.visible(false)

        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    binding.homeprogressbar.visible(false)
                    updateUI(it.value.user)
                }

                is Resource.loading ->{
                    binding.homeprogressbar.visible(true)
                }
            }
        })

        /*
        //------------------implement logout functionality
        binding.logout_button.setOnClickListener{
            logout()
        }*/
    }

    private fun updateUI(user: User) {
        with(binding){
            binding.userIdText.text=user.id.toString()
            binding.userEmailText.text=user.email.toString()
            binding.userNameText.text=user.name.toString()
        }
    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = HomefragmentBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): UserRepository {
        val token= runBlocking { userPreferences.authToken.first() }
         val api=remoteDataSource.buildApi(UserApi::class.java,token)
        return UserRepository(api)
    }
}