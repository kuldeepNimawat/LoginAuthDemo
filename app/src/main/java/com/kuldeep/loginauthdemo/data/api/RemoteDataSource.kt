package com.kuldeep.loginauthdemo.data.api

import com.kuldeep.loginauthdemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object{
        private const val BaseUrl="http://codingsimplifiedcoding.tech/mywebapp/public/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
    //---------------for adding authtoken in header--------------
    authToken: String?=null
    //------------------------------------------------------------
    ): Api{
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(OkHttpClient.Builder()
                //------------------for adding auth token--------------
                .addInterceptor { chain ->  
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Authorization","Bearer$authToken")
                    }.build())
                }
                 //----------------------------------------------------
                .also { client ->
                if(BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}