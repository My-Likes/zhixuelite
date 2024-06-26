package com.zhixue.lite.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhixue.lite.core.model.UserInfo

@Entity(
    tableName = "user_info"
)
data class UserInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "class_name")
    val className: String,
    @ColumnInfo(name = "school_name")
    val schoolName: String,
    @ColumnInfo(name = "grant_ticket")
    val grantTicket: String
)

fun UserInfoEntity.asExternalModel(): UserInfo = UserInfo(
    id = id,
    avatar = avatar,
    name = name,
    className = className,
    schoolName = schoolName,
    grantTicket = grantTicket
)