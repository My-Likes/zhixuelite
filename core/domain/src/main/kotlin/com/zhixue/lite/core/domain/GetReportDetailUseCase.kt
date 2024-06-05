package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.PaperRepository
import com.zhixue.lite.core.model.FormatPaperInfo
import com.zhixue.lite.core.model.ReportDetail
import com.zhixue.lite.core.model.ScoreInfo
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.math.roundToInt

class GetReportDetailUseCase @Inject constructor(
    private val paperRepository: PaperRepository
) {
    suspend operator fun invoke(reportId: String): ReportDetail {
        val paperInfoList = paperRepository.getPaperInfoList(reportId)

        check(paperInfoList.isNotEmpty())

        var totalUserScore = BigDecimal.ZERO
        var totalStandardScore = BigDecimal.ZERO

        val overviews = mutableListOf<FormatPaperInfo>()

        for (paperInfo in paperInfoList) {
            val paperId = paperInfo.id
            val userScore = paperInfo.userScore?.toBigDecimal()
            val standardScore = paperInfo.standardScore.toBigDecimal()
            var classRank = paperInfo.classRank
            val classPercentile = paperInfo.classPercentile
            val classTrendInfo = paperInfo.classTrendInfo

            if (classRank == null && classPercentile != null && classTrendInfo != null) {
                classRank = calculateRank(classTrendInfo.studentNumber, classPercentile)
            }

            if (!paperId.contains("!")) {
                if (userScore != null) {
                    totalUserScore += userScore
                }
                totalStandardScore += standardScore
            }

            overviews.add(
                FormatPaperInfo(
                    id = paperId,
                    subjectName = paperInfo.subjectName,
                    userScore = userScore?.transformPlainString() ?: "-",
                    standardScore = standardScore.transformPlainString(),
                    scoreRate = paperInfo.scoreRate,
                    level = classTrendInfo?.level.orEmpty(),
                    classRank = classRank?.toString() ?: "-",
                    direction = classTrendInfo?.direction
                )
            )
        }

        val totalScoreInfo = ScoreInfo(
            userScore = totalUserScore.transformPlainString(),
            standardScore = totalStandardScore.transformPlainString(),
            scoreRate = totalUserScore.toFloat() / totalStandardScore.toFloat()
        )

        return ReportDetail(totalScoreInfo, overviews)
    }
}

private fun calculateRank(studentNumber: Int, classPercentile: Double): Int {
    return (studentNumber - (studentNumber - 1) * (100 - classPercentile) / 100).roundToInt()
}

private fun BigDecimal.transformPlainString(): String {
    return stripTrailingZeros().toPlainString()
}