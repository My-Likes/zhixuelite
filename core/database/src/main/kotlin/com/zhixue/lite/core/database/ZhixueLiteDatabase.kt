package com.zhixue.lite.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhixue.lite.core.database.dao.PaperInfoDao
import com.zhixue.lite.core.database.dao.RemotePageDao
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.database.dao.TrendInfoDao
import com.zhixue.lite.core.database.dao.UserDao
import com.zhixue.lite.core.database.model.PaperInfoEntity
import com.zhixue.lite.core.database.model.RemotePageEntity
import com.zhixue.lite.core.database.model.ReportInfoEntity
import com.zhixue.lite.core.database.model.TrendInfoEntity
import com.zhixue.lite.core.database.model.UserEntity

@Database(
    version = 1,
    entities = [
        UserEntity::class,
        RemotePageEntity::class,
        ReportInfoEntity::class,
        PaperInfoEntity::class,
        TrendInfoEntity::class
    ]
)
internal abstract class ZhixueLiteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remotePageDao(): RemotePageDao
    abstract fun reportInfoDao(): ReportInfoDao
    abstract fun paperInfoDao(): PaperInfoDao
    abstract fun trendInfoDao(): TrendInfoDao
}