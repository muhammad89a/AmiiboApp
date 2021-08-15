package com.muhammad89a.amiiboapp.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import androidx.room.FtsOptions.Order

import androidx.room.Update




@Dao
interface AmiiboDao {

    @Query("SELECT * FROM Amiibos")
    fun getAllAmiibos(): PagingSource<Int, AmiiboDTO>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAmiibos(items:List<AmiiboDTO>)

    @Query("DELETE FROM Amiibos")
    suspend fun clearAmiibos()

    @Query("SELECT * FROM Amiibos WHERE head = :head and tail=:tail ")
    suspend fun getAmiibosByID(head: String,tail: String): AmiiboDTO

    @Update(entity = AmiiboDTO::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun purchaseAmiiboByID(amiibo: AmiiboDTO)


}