package com.muhammad89a.amiiboapp.di

import android.content.Context
import androidx.room.Room
import com.muhammad89a.amiiboapp.data.db.AmiiboDao
import com.muhammad89a.amiiboapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, "AmiiboDB.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAmiiboDao(appDatabase: AppDatabase): AmiiboDao{
        return appDatabase.amiiboDao()
    }
}