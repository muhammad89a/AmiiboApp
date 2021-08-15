package com.muhammad89a.amiiboapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muhammad89a.amiiboapp.data.db.AppDatabase
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import com.muhammad89a.amiiboapp.data.network.NetworkApis
import com.muhammad89a.amiiboapp.data.paging.AmiiboRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AmiiboRepository @Inject constructor(
    private val service: NetworkApis,
    private val database: AppDatabase
) {

    @ExperimentalPagingApi
    fun fetchAmiibos(): Flow<PagingData<AmiiboDTO>> {
        val pagingSourceFactory = { database.amiiboDao().getAllAmiibos()!! }
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, maxSize = 3000, enablePlaceholders = true),
            remoteMediator = AmiiboRemoteMediator(database,service),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun puchaseAmiibo(amibo:AmiiboDTO):Flow<AmiiboDTO> = flow {
        database.amiiboDao().purchaseAmiiboByID(amibo)
        emit(amibo)
    }.flowOn(Dispatchers.IO)


    companion object {
        private const val NETWORK_PAGE_SIZE = 800
    }
}
