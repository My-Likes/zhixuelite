package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.PaperRepository
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.FormatPaperInfo
import com.zhixue.lite.core.model.ReportDetail
import javax.inject.Inject
import kotlin.math.roundToInt

class GetReportDetailUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val paperRepository: PaperRepository
) {
    suspend operator fun invoke(reportId: String): ReportDetail {
        return ReportDetail(
            scoreInfo = reportRepository.getReportScoreInfo(reportId),
            overviews = paperRepository.getPaperInfoList(reportId)
                .map { paperInfo ->
                    val classRank = paperInfo.classRank ?: calculateRank(
                        studentNumber = paperInfo.classTrendInfo?.studentNumber,
                        classPercentile = paperInfo.classPercentile
                    )
                    FormatPaperInfo(
                        id = paperInfo.id,
                        subjectName = paperInfo.subjectName,
                        userScore = paperInfo.userScore?.toPlainString() ?: "-",
                        standardScore = paperInfo.standardScore.toPlainString(),
                        scoreRate = paperInfo.scoreRate,
                        level = paperInfo.classTrendInfo?.level.orEmpty(),
                        classRank = classRank?.toString() ?: "-",
                        direction = paperInfo.classTrendInfo?.direction
                    )
                }
                .also { check(it.isNotEmpty()) }
        )
    }
}

private fun calculateRank(studentNumber: Int?, classPercentile: Double?): Int? {
    return if (studentNumber != null && classPercentile != null) {
        (studentNumber - (studentNumber - 1) * (100 - classPercentile) / 100).roundToInt()
    } else {
        null
    }
}

private fun Double.toPlainString(): String {
    return toBigDecimal().stripTrailingZeros().toPlainString()
}