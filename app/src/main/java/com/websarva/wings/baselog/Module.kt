package com.websarva.wings.baselog

import android.content.Context
import androidx.room.Room
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "task_database").build()

    @Provides
    fun provideDao(db: AppDatabase) = db.logDao()
}