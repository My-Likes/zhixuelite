package com.zhixue.lite.core.datastore.model

import com.zhixue.lite.core.model.UserData
import com.zhixue.lite.core.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val id: String = "",
    val avatar: String = "",
    val name: String = "",
    val className: String = "",
    val schoolName: String = "",
    val grantTicket: String = ""
)

fun UserPreference.asExternalModel(): UserData = UserData(
    id = id,
    info = UserInfo(
        avatar = avatar,
        name = name,
        className = className,
        schoolName = schoolName
    ),
    grantTicket = grantTicket
)