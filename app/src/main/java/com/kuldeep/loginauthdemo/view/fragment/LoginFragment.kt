package com.kuldeep.loginauthdemo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kuldeep.loginauthdemo.data.api.AuthApi
import com.kuldeep.loginauthdemo.data.repository.LoginRepository
import com.kuldeep.loginauthdemo.data.api.Resource
import com.kuldeep.loginauthdemo.databinding.LoginfragmentBinding
import com.kuldeep.loginauthdemo.util.enable
import com.kuldeep.loginauthdemo.util.handleApiError
import com.kuldeep.loginauthdemo.util.startNewActivity
import com.kuldeep.loginauthdemo.util.visible
import com.kuldeep.loginauthdemo.view.acitivity.HomeActivity
import com.kuldeep.loginauthdemo.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.loginfragment.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<LoginViewModel, LoginfragmentBinding, LoginRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.myprogressbar.visible(false)
        binding.loginButton.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            //binding.myprogressbar.visible(false)//when we are not handling error and loading function
            binding.myprogressbar.visible(it is Resource.loading)
            when(it){
                is Resource.Success ->{
                    //Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.access_token!!)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it){
                    login()
                }
            /*{
                    //Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                    //-----------login url not working so I put this here--------------
                    //binding.myprogressbar.visible(false)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }*/
            }
        })

        binding.userPassword.addTextChangedListener {
            val email=binding.userEmailId.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.loginButton.setOnClickListener(){
            login()
        }
    }

    private fun login(){
        val email=binding.userEmailId.text.toString().trim()
        val password=binding.userPassword.text.toString().trim()
        //binding.myprogressbar.visible(true)
        viewModel.login(email,password)
    }

    override fun getViewModel()= LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= LoginfragmentBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= LoginRepository(remoteDataSource.buildApi(AuthApi::class.java),userPreferences )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                return LoginFragment()
            }
    }
}