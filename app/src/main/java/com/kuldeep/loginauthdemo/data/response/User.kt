package com.kuldeep.loginauthdemo.data.response

data class User(
    //val access_token: String,  //-----------for auth api---
    val access_token: String?,  //------------for get user api-------
    val created_at: String,
    val email: String,
    val email_varified_at: Any,
    val id: Int,
    val name: String,
    val updated_at: String
)