package com.zhixue.lite.core.datastore.model

import com.zhixue.lite.core.model.UserData
import com.zhixue.lite.core.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val id: String = "",
    val avatar: String = "",
    val name: String = "",
    val className: String = "",
    val schoolName: String = "",
    val grantTicket: String = ""
)

fun UserPreferences.asExternalModel(): UserData = UserData(
    info = UserInfo(
        id = id,
        avatar = avatar,
        name = name,
        className = className,
        schoolName = schoolName,
        grantTicket = grantTicket
    )
)