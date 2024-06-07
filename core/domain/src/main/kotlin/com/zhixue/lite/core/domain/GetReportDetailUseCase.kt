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
        val paperInfoList = paperRepository.getPaperInfoList(reportId)

        check(paperInfoList.isNotEmpty())

        val overviews = mutableListOf<FormatPaperInfo>()

        for (paperInfo in paperInfoList) {
            var classRank = paperInfo.classRank
            val classPercentile = paperInfo.classPercentile
            val classTrendInfo = paperInfo.classTrendInfo

            if (classRank == null && classPercentile != null && classTrendInfo != null) {
                classRank = calculateRank(classTrendInfo.studentNumber, classPercentile)
            }

            overviews.add(
                FormatPaperInfo(
                    id = paperInfo.id,
                    subjectName = paperInfo.subjectName,
                    userScore = paperInfo.userScore?.toPlainString() ?: "-",
                    standardScore = paperInfo.standardScore.toPlainString(),
                    scoreRate = paperInfo.scoreRate,
                    level = classTrendInfo?.level.orEmpty(),
                    classRank = classRank?.toString() ?: "-",
                    direction = classTrendInfo?.direction
                )
            )
        }

        return ReportDetail(
            scoreInfo = reportRepository.getReportScoreInfo(reportId),
            overviews = overviews
        )
    }
}

private fun calculateRank(studentNumber: Int, classPercentile: Double): Int {
    return (studentNumber - (studentNumber - 1) * (100 - classPercentile) / 100).roundToInt()
}

private fun Double.toPlainString(): String {
    return toBigDecimal().stripTrailingZeros().toPlainString()
}