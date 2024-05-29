package com.zhixue.lite.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "subject_diagnosis",
    primaryKeys = ["report_id", "subject_code"]
)
data class SubjectDiagnosisInfoEntity(
    @ColumnInfo(name = "report_id")
    val reportId: String,
    @ColumnInfo(name = "subject_code")
    val subjectCode: String,
    @ColumnInfo(name = "user_level")
    val userLevel: Double,
    @ColumnInfo(name = "average_level")
    val averageLevel: Double
)