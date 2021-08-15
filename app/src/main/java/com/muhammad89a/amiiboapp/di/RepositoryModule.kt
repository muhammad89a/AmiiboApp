package com.muhammad89a.amiiboapp.di

import com.muhammad89a.amiiboapp.data.repository.AmiiboRepository
import com.muhammad89a.amiiboapp.data.db.AppDatabase
import com.muhammad89a.amiiboapp.data.network.NetworkApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAmiiboRepository(
        service: NetworkApis,
        database: AppDatabase
    ): AmiiboRepository {
        return AmiiboRepository(service, database)
    }
}