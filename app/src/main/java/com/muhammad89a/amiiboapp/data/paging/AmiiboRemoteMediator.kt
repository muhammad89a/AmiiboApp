package com.muhammad89a.amiiboapp.data.paging


import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.muhammad89a.amiiboapp.data.db.AppDatabase
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import com.muhammad89a.amiiboapp.data.network.NetworkApis
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class AmiiboRemoteMediator(
    private val database: AppDatabase,
    private val networkApis: NetworkApis
) :
    RemoteMediator<Int, AmiiboDTO>() {

    private val amiiboDaoDao = database.amiiboDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        Log.d("AmiiboRemoteMediator", "initialize = ${InitializeAction.SKIP_INITIAL_REFRESH}")
        return if (false)
        {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AmiiboDTO>
    ): MediatorResult {
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            Log.d("AmiiboRemoteMediator", "loadType = $loadType")

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null

                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> {
                    Log.d("AmiiboRemoteMediator", "LoadType.PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.d("AmiiboRemoteMediator", "LoadType.APPEND")

                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.head+lastItem.tail;
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = networkApis.getAllAmiibos()
            Log.d("AmiiboRemoteMediator", "response=$response")

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    amiiboDaoDao.getAllAmiibos()
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                amiiboDaoDao.insertAllAmiibos(response.amiibo)
            }

            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}