package com.zhixue.lite.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCasInfo(
    @SerialName("role")
    val role: String,
    @SerialName("token")
    val token: String,
    @SerialName("childrens")
    val children: List<NetworkChildrenInfo>? = null,
    @SerialName("userInfo")
    val userInfo: NetworkCasUserInfo,
    @SerialName("clazzInfo")
    val classInfo: NetworkClassInfo? = null,
)

@Serializable
data class NetworkChildrenInfo(
    @SerialName("id")
    val id: String,
    @SerialName("userInfo")
    val userInfo: NetworkCasUserInfo,
    @SerialName("clazzInfo")
    val classInfo: NetworkClassInfo
)

@Serializable
data class NetworkCasUserInfo(
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("school")
    val schoolInfo: SchoolInfo? = null
) {
    @Serializable
    data class SchoolInfo(
        @SerialName("schoolName")
        val name: String
    )
}

@Serializable
data class NetworkClassInfo(
    @SerialName("name")
    val name: String
)