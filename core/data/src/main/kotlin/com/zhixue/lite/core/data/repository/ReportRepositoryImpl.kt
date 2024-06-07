package com.zhixue.lite.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zhixue.lite.core.database.dao.RemotePageDao
import com.zhixue.lite.core.database.dao.ReportDao
import com.zhixue.lite.core.database.model.ReportInfoEntity
import com.zhixue.lite.core.database.model.asExternalModel
import com.zhixue.lite.core.model.ReportInfo
import com.zhixue.lite.core.model.ScoreInfo
import com.zhixue.lite.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ReportRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val remotePageDao: RemotePageDao,
    private val reportDao: ReportDao,
    private val networkDataSource: NetworkDataSource
) : ReportRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getReportInfoList(reportType: String): Flow<PagingData<ReportInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ReportInfoRemoteMediator(
                reportType = reportType,
                userRepository = userRepository,
                remotePageDao = remotePageDao,
                reportDao = reportDao,
                networkDataSource = networkDataSource
            ),
            pagingSourceFactory = {
                reportDao.reportInfoPagingSource(userRepository.userId, reportType)
            }
        ).flow.map { pagingData ->
            pagingData.map(ReportInfoEntity::asExternalModel)
        }
    }

    override suspend fun getReportScoreInfo(reportId: String): ScoreInfo {
        return reportDao.getReportScoreInfo(userRepository.userId, reportId).asExternalModel()
    }
}