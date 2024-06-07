package com.zhixue.lite.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhixue.lite.core.database.model.ReportInfoEntity
import com.zhixue.lite.core.database.model.ReportScoreInfo

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportInfoList(entities: List<ReportInfoEntity>)

    @Query(
        """
            SELECT 
                SUM(user_score) AS user_score,
                SUM(standard_score) AS standard_score,
                ROUND(user_score / standard_score, 2) AS score_rate
            FROM paper_info
            WHERE 
                user_id = :userId
                AND report_id = :reportId
                AND id NOT LIKE '%!%'
        """
    )
    suspend fun getReportScoreInfo(userId: String, reportId: String): ReportScoreInfo

    @Query(
        """
            SELECT id FROM report_info
            WHERE user_id = :userId AND type = :reportType
            ORDER BY create_date DESC
            LIMIT 1
        """
    )
    suspend fun getReportInfoLatestId(userId: String, reportType: String): String?

    @Query(
        """
            SELECT * FROM report_info
            WHERE user_id = :userId AND type = :reportType
            ORDER BY create_date DESC
        """
    )
    fun reportInfoPagingSource(
        userId: String, reportType: String
    ): PagingSource<Int, ReportInfoEntity>

    @Query(
        """
            DELETE FROM report_info
            WHERE user_id = :userId AND type = :reportType
        """
    )
    suspend fun deleteReportInfoList(userId: String, reportType: String)
}