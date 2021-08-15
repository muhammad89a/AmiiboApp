package com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muhammad89a.amiiboapp.data.repository.AmiiboRepository
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AmiibosGridViewModel @Inject constructor(
    private val repository: AmiiboRepository
) : ViewModel() {

    private var amiiboList: Flow<PagingData<AmiiboDTO>>? = null

    fun loadAmiibos(): Flow<PagingData<AmiiboDTO>> {
        if(amiiboList == null) {
            Log.d("AmiibosGridViewModel", "fetchAmiibos")
            val newAmiibos = repository.fetchAmiibos().cachedIn(viewModelScope)
            amiiboList = newAmiibos
        }
        return amiiboList!!
    }

}