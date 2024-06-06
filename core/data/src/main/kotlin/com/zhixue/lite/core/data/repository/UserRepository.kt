package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val userId: String

    val token: String

    suspend fun userLogin(username: String, password: String, captcha: String)

    suspend fun userLogin(grantTicket: String)

    fun getUserInfo(): Flow<UserInfo>

    suspend fun setUserInfo(
        id: String, avatar: String,
        name: String, className: String, schoolName: String,
        grantTicket: String
    )

    suspend fun getGrantTicket(): String
}