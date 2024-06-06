package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.database.dao.UserInfoDao
import com.zhixue.lite.core.database.model.UserInfoEntity
import com.zhixue.lite.core.datastore.PreferencesDataSource
import com.zhixue.lite.core.datastore.model.UserPreferences
import com.zhixue.lite.core.datastore.model.asExternalModel
import com.zhixue.lite.core.model.UserData
import com.zhixue.lite.core.model.UserInfo
import com.zhixue.lite.core.network.NetworkDataSource
import com.zhixue.lite.core.network.model.NetworkSsoInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userInfoDao: UserInfoDao,
    private val networkDataSource: NetworkDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : UserRepository {

    override var userId: String = ""
        private set

    override var token: String = ""
        private set

    private val currentUserData: Flow<UserData> = preferencesDataSource.userPreferences
        .map(UserPreferences::asExternalModel)

    override suspend fun userLogin(username: String, password: String, captcha: String) {
        handleLogin(networkDataSource.ssoLogin(username, password, captcha))
    }

    override suspend fun userLogin(grantTicket: String) {
        handleLogin(networkDataSource.ssoLogin(grantTicket))
    }

    override fun getUserInfo(): Flow<UserInfo> {
        return currentUserData.map { it.info }
    }

    override suspend fun setUserInfo(
        id: String, avatar: String,
        name: String, className: String, schoolName: String,
        grantTicket: String
    ) {
        userInfoDao.insertUserInfo(
            UserInfoEntity(id, avatar, name, className, schoolName, grantTicket)
        )
        preferencesDataSource.setUserInfo(
            id, avatar, name, className, schoolName, grantTicket
        )
    }

    override suspend fun getGrantTicket(): String {
        return currentUserData.first().info.grantTicket
    }

    private suspend fun handleLogin(ssoInfo: NetworkSsoInfo) {
        val casInfo = networkDataSource.casLogin(ssoInfo.accessTicket, ssoInfo.userId)

        val token = casInfo.token
        val currentUserId = networkDataSource.getUserInfo(token).currentUserId

        val (userInfo, classInfo) = when (casInfo.role) {
            "student" -> {
                casInfo.userInfo to casInfo.classInfo!!
            }

            "parent" -> {
                casInfo.children
                    ?.find { it.id == currentUserId }
                    ?.let { it.userInfo to it.classInfo }
                    ?: error("请确认是否已绑定学生账号")
            }

            else -> {
                error("账号类型暂不支持")
            }
        }

        this.userId = currentUserId
        this.token = token

        setUserInfo(
            id = currentUserId,
            name = userInfo.name,
            avatar = userInfo.avatar.orEmpty(),
            className = classInfo.name,
            schoolName = userInfo.schoolInfo?.name.orEmpty(),
            grantTicket = ssoInfo.grantTicket
        )
    }
}