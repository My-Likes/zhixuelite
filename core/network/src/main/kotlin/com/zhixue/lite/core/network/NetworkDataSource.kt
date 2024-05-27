package com.zhixue.lite.core.network

import com.zhixue.lite.core.network.model.NetworkCasInfo
import com.zhixue.lite.core.network.model.NetworkLevelTrend
import com.zhixue.lite.core.network.model.NetworkReportInfoPage
import com.zhixue.lite.core.network.model.NetworkReportMain
import com.zhixue.lite.core.network.model.NetworkSsoInfo
import com.zhixue.lite.core.network.model.NetworkUserInfo

interface NetworkDataSource {

    suspend fun ssoLogin(username: String, password: String, captcha: String): NetworkSsoInfo

    suspend fun ssoLogin(ticket: String): NetworkSsoInfo

    suspend fun casLogin(at: String, userId: String): NetworkCasInfo

    suspend fun getUserInfo(token: String): NetworkUserInfo

    suspend fun getReportInfoPage(type: String, page: Int, token: String): NetworkReportInfoPage

    suspend fun getReportMain(reportId: String, token: String): NetworkReportMain

    suspend fun getLevelTrend(reportId: String, subjectId: String, token: String): NetworkLevelTrend
}