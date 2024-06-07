package com.zhixue.lite.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zhixue.lite.core.data.model.asEntity
import com.zhixue.lite.core.database.dao.RemotePageDao
import com.zhixue.lite.core.database.dao.ReportDao
import com.zhixue.lite.core.database.model.RemotePageEntity
import com.zhixue.lite.core.database.model.ReportInfoEntity
import com.zhixue.lite.core.network.NetworkDataSource

private const val LABEL_PREFIX = "REPORT_INFO"
private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
internal class ReportInfoRemoteMediator(
    private val reportType: String,
    private val userRepository: UserRepository,
    private val remotePageDao: RemotePageDao,
    private val reportDao: ReportDao,
    private val networkDataSource: NetworkDataSource
) : RemoteMediator<Int, ReportInfoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return try {
            val localLatestId =
                reportDao.getReportInfoLatestId(userRepository.userId, reportType)
            val networkLatestId = networkDataSource.getReportInfoPage(
                reportType, STARTING_PAGE, userRepository.token
            ).reportInfoList.first().id

            check(localLatestId != networkLatestId)

            InitializeAction.LAUNCH_INITIAL_REFRESH
        } catch (e: Exception) {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReportInfoEntity>
    ): MediatorResult {
        return try {
            val label = "${LABEL_PREFIX}_${reportType.uppercase()}"
            // 获取待加载页
            val loadPage = when (loadType) {
                LoadType.REFRESH -> {
                    STARTING_PAGE
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    remotePageDao.getRemotePageNextPage(userRepository.userId, label)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            // 从网络获取报告列表
            val networkReportInfoPage =
                networkDataSource.getReportInfoPage(reportType, loadPage, userRepository.token)
            // 刷新时，删除之前缓存
            if (loadType == LoadType.REFRESH) {
                remotePageDao.deleteRemotePage(userRepository.userId, label)
                reportDao.deleteReportInfoList(userRepository.userId, reportType)
            }
            // 插入新的数据
            remotePageDao.insertRemotePage(
                RemotePageEntity(userRepository.userId, label, nextPage = loadPage + 1)
            )
            reportDao.insertReportInfoList(
                networkReportInfoPage.reportInfoList.map {
                    it.asEntity(userRepository.userId, reportType)
                }
            )

            MediatorResult.Success(endOfPaginationReached = !networkReportInfoPage.hasNextPage)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}